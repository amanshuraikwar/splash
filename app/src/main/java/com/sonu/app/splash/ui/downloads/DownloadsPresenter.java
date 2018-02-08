package com.sonu.app.splash.ui.downloads;

import android.app.Activity;
import android.util.Log;
import android.util.Pair;

import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.util.LogUtils;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amanshuraikwar on 24/12/17.
 */

public class DownloadsPresenter
        extends BasePresenterImpl<DownloadsContract.View>
        implements DownloadsContract.Presenter {

    private static final String TAG = LogUtils.getLogTag(DownloadsPresenter.class);

    private Disposable getDownloadedPhotosDisp;

    @Inject
    public DownloadsPresenter(AppBus appBus, DataManager dataManager, Activity activity) {
        super(appBus, dataManager, activity);
    }

    @Override
    public void attachView(DownloadsContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        if (wasViewRecreated) {

            runDownloadStatusTask();
        }
    }

    private void runDownloadStatusTask() {

        getDownloadedPhotosDisp = getDataManager()
                .getRunningPausedPendingDownloads()
                .flatMapIterable(items -> items)
                .flatMap(item -> check(item), (item1, item2) -> getPair(item1, item2))
                .flatMap(item3 -> update(item3))
                .toList()
                .flatMapObservable(item4 -> getDataManager().getPhotoDownloads())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<PhotoDownload>>() {
                               @Override
                               public void accept(List<PhotoDownload> photoDownloads)
                                       throws Exception {

                                   Log.i(TAG,
                                           "getPhotoDownloads:accept:photosNo="
                                                   +photoDownloads.size());
                                   getView().displayPhotos(photoDownloads);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                                throwable.printStackTrace();
                                getView().showError();
                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                getView().hideLoading();
                            }
                        },
                        new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                getView().showLoading();
                            }
                        });
    }

    private Observable<PhotoDownload.Status> check(PhotoDownload photoDownload) {

        return Observable.fromCallable(new Callable<PhotoDownload.Status>() {
            @Override
            public PhotoDownload.Status call() throws Exception {
                return getDataManager().checkDownloadStatus(photoDownload.getDownloadReference());
            }
        });
    }

    private Pair<PhotoDownload, PhotoDownload.Status> getPair(PhotoDownload photoDownload,
                                                              PhotoDownload.Status status) {
        return new Pair<>(photoDownload, status);
    }

    private Observable<Boolean> update(Pair<PhotoDownload, PhotoDownload.Status> pair) {

        pair.first.setStatus(pair.second);
        return getDataManager().updatePhotoDownload(pair.first);
    }

    @Override
    public void getPhotos() {

        runDownloadStatusTask();
    }

    @Override
    public void detachView() {
        super.detachView();

        getDownloadedPhotosDisp.dispose();
    }
}
