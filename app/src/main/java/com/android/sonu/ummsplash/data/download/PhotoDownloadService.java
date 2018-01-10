package com.android.sonu.ummsplash.data.download;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;

import com.android.sonu.ummsplash.R;
import com.android.sonu.ummsplash.bus.AppBus;
import com.android.sonu.ummsplash.data.DataManager;
import com.android.sonu.ummsplash.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import dagger.android.DaggerService;
import dagger.internal.Beta;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by amanshuraikwar on 22/12/17.
 */

public class PhotoDownloadService extends DaggerService {

    private static final String TAG = LogUtils.getLogTag(PhotoDownloadService.class);

    public static final String KEY_PHOTO_DOWNLOAD = "KEY_PHOTO_DOWNLOAD";
    public static final String KEY_ACTION = "KEY_ACTION";
    public static final String KEY_PHOTO_ID = "KEY_PHOTO_ID";

    public static final String ACTION_ADD_TO_DOWNLOAD_QUEUE = "ADD_TO_DOWNLOAD_QUEUE";
    public static final String ACTION_RETRY_CURRENT_DOWNLOAD = "RETRY_CURRENT_DOWNLOAD";
    public static final String ACTION_CANCEL_DOWNLOAD = "CANCEL_DOWNLOAD";

    public enum STATE {IDLE, DOWNLOADING, WAITING}

    private STATE state = STATE.IDLE;

    private volatile Queue<PhotoDownload> downloadQueue;
    private volatile Set<String> downloadingIds;

    private int curDownloadProgress;
    private String curError;
    private Disposable curDisposable;

    @Inject
    AppBus appBus;

    @Inject
    DataManager dataManager;

    private final ProgressListener progressListener =
            new ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    Log.i(TAG,
                            "downloadUpdate:"+((100 * bytesRead) / contentLength)+" done");
                    curDownloadProgress = (int) ((100 * bytesRead) / contentLength);

                    // * notifying attached presenters
                    // * this is a light and frequent update
                    //   thus notified in a PUSH fashion
                    appBus.updateDownloadProgress.onNext(new Pair<>(bytesRead, contentLength));
                }
    };

    private final OkHttpClient okHttpClient =
            new OkHttpClient
                    .Builder()
                    .addNetworkInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Chain chain)
                                        throws IOException {

                                    Response originalResponse = chain.proceed(chain.request());

                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            })
                    .build();

    @Override
    public void onCreate() {
        super.onCreate();

        // initial state is always IDLE
        state = STATE.IDLE;

        // initialising download cache
        downloadQueue = new LinkedList<>();
        downloadingIds = new HashSet<>();

        // setting download session active
        dataManager.getDownloadSession().setSessionActive(true);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getStringExtra(KEY_ACTION);

        Log.d(TAG, "onStartCommand:called");
        Log.i(TAG, "onStartCommand:action="+action);

        switch (action) {
            case ACTION_ADD_TO_DOWNLOAD_QUEUE:

                PhotoDownload newPhotoDownload =
                        intent.getParcelableExtra(KEY_PHOTO_DOWNLOAD);

                if (newPhotoDownload != null) {
                    handleNewPhotoDownload(newPhotoDownload);
                } else {
                    Log.e(TAG, "onStartCommand:PhotoDownload is null");
                }

                break;

            case ACTION_RETRY_CURRENT_DOWNLOAD:
                handleRetryDownload();
                break;

            case ACTION_CANCEL_DOWNLOAD:

                String photoId =
                        intent.getStringExtra(KEY_PHOTO_ID);

                if (photoId != null) {
                    handleCancelDownload(photoId);
                } else {
                    Log.e(TAG, "onStartCommand:photoId is null");
                }

                break;
            default:
                Log.wtf(TAG, "onStartCommand:unsupported action");
        }

        // to only start the service after being killed if pending
        // intents are remaining to be delivered
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // to prevent binding
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // de-activating download session
        dataManager.getDownloadSession().setSessionActive(false);
    }

    private void handleNewPhotoDownload(PhotoDownload photoDownload) {

        Log.d(TAG, "handleNewPhotoDownload:called");
        Log.i(TAG, "handleNewPhotoDownload:photoDownload="+photoDownload);

        // add to download queue only if it does not already exist
        if (!downloadingIds.contains(photoDownload.getPhotoId())) {
            downloadQueue.add(photoDownload);
            downloadingIds.add(photoDownload.getPhotoId());

            // if the state is IDLE --> first entry in download queue
            // thus start download
            if (state == STATE.IDLE) {
                startDownload();
            } else {
                // at least notify ui of download queue change
                notifyUi();
            }
        } else {
            sendQuickMessageToUi(getString(R.string.photo_exist_in_dowload_queue));
        }
    }

    private void handleRetryDownload() {

        // can only retry download if in waiting state
        if (state == STATE.WAITING) {
            startDownload();
        }
    }

    private void handleCancelDownload(String photoId) {

        // cannot cancel download if in IDLE state
        if (state == STATE.WAITING || state == STATE.DOWNLOADING) {
            cancelDownload(photoId);
        }
    }

    private void startDownload() {

        Log.d(TAG, "startDownload:called");

        final PhotoDownload photoDownload = downloadQueue.peek();

        Log.i(TAG, "startDownload:photoDownload="+photoDownload);

        if (photoDownload != null) {

            sendQuickMessageToUi(getString(R.string.download_started));

            curDisposable =
                    Observable.fromCallable(
                            new Callable<ResponseBody>() {

                                @Override
                                public ResponseBody call() throws Exception {
                                    return startDownloadAct(photoDownload);
                                }
                            })
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<ResponseBody>() {

                                @Override
                                public void accept(ResponseBody responseBody) throws Exception {

                                    onDownloadComplete();
                                }
                            }, new Consumer<Throwable>() {

                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    state = STATE.WAITING;
                                    notifyUi(throwable.getMessage());
                                }
                            });
        } else {
            Log.wtf(TAG, "startDownload:queue head is null");
        }
    }

    private ResponseBody startDownloadAct(PhotoDownload photoDownload) throws IOException {

        state = STATE.DOWNLOADING;

        notifyUi();

        Request request = new Request.Builder()
                .url(photoDownload.getDownloadUrl())
                .build();

        Response response = okHttpClient.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.message());
        }

        saveFile(response.body(), photoDownload.getDownloadedFileName());

        return response.body();
    }


    private void notifyUi() {
        notifyUi(null);
    }

    private void notifyUi(String error) {
        curError = error;

        // update state in session
        dataManager.getDownloadSession().setDownloadState(new DownloadState(this));

        // * notify any attached presenter about the change
        // * notified in a PULL fashion
        appBus.onDownloadStateChange.onNext(0);
    }

    private void sendQuickMessageToUi(String error) {

        // pushing quick error to UI
        appBus.sendQuickMessage.onNext(error);
    }

    @Beta
    private void cancelDownload(String photoId) {

        //current downloading image is to be cancelled
        if (photoId.equals(downloadQueue.peek().getPhotoId())) {

            // todo : fix
            // * does not stop the download going on in a separate thread
            //   just stops observing the OBSERVABLE

            curDisposable.dispose();

            // removing from queue
            downloadingIds.remove(downloadQueue.poll().getPhotoId());

            sendQuickMessageToUi(getString(R.string.download_cancelled));

            if (downloadQueue.size() != 0) {
                // if queue is not empty start next download
                startDownload();
            } else {
                // if queue is empty then notify ui
                state = STATE.IDLE;
                notifyUi();

                // stop the service
                stopSelf();
            }
        } else { // one of the queued downloads is to be cancelled

            for (PhotoDownload photoDownload : downloadQueue) {

                // just remove them from queue
                if (photoDownload.getPhotoId().equals(photoId)) {
                    downloadQueue.remove(photoDownload);
                    downloadingIds.remove(photoId);

                    sendQuickMessageToUi(getString(R.string.download_cancelled));
                    notifyUi();
                    break;
                }
            }

        }
    }

    private void onDownloadComplete(){

        // remove current image from download queue
        downloadingIds.remove(downloadQueue.poll().getPhotoId());

        sendQuickMessageToUi(getString(R.string.download_complete));

        if (downloadQueue.size() != 0) {
            // if queue has other downloads them start download
            startDownload();
        } else {
            // if nothing left to download, notify ui
            state = STATE.IDLE;
            notifyUi();

            // stop the service
            stopSelf();
        }
    }

    private void saveFile(ResponseBody responseBody, String fileName) throws IOException {

        File outputFile =
                new File(
                        Environment
                                .getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_DOWNLOADS),
                        fileName);

        FileOutputStream fos = new FileOutputStream(outputFile);
        fos.write(responseBody.bytes());
        fos.close();
    }

    private static class ProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private final ProgressListener progressListener;
        private BufferedSource bufferedSource;

        ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
            this.responseBody = responseBody;
            this.progressListener = progressListener;
        }

        @Override public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override public long contentLength() {
            return responseBody.contentLength();
        }

        @Override public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    progressListener
                            .update(totalBytesRead,
                                    responseBody.contentLength(),
                                    bytesRead == -1);
                    return bytesRead;
                }
            };
        }
    }

    interface ProgressListener {
        void update(long bytesRead, long contentLength, boolean done);
    }

    public static class DownloadState {

        public STATE state;
        public int downloadQueueLength;
        public String curDownloadFileName;
        public int curDownloadProgress;
        public String error;
        public String curDownloadPhotoId;

        public DownloadState(PhotoDownloadService service) {
            state = service.state;
            downloadQueueLength = service.downloadQueue.size();
            if (service.downloadQueue.peek() != null) {
                curDownloadFileName = service.downloadQueue.peek().getDownloadedFileName();
                curDownloadPhotoId = service.downloadQueue.peek().getPhotoId();
            }
            curDownloadProgress = service.curDownloadProgress;
            error = service.curError;
        }

        @Override
        public String toString() {
            return "DownloadState{" +
                    "state=" + state +
                    ", downloadQueueLength=" + downloadQueueLength +
                    ", curDownloadFileName='" + curDownloadFileName + '\'' +
                    ", curDownloadProgress=" + curDownloadProgress +
                    ", error='" + error + '\'' +
                    '}';
        }
    }
}
