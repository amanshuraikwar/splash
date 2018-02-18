package com.sonu.app.splash.ui.downloads;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;

import com.sonu.app.splash.data.local.room.photodownload.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amanshuraikwar on 24/12/17.
 */

public class DownloadsPresenter
        extends BasePresenterImpl<DownloadsContract.View>
        implements DownloadsContract.Presenter {

    private static final String TAG = LogUtils.getLogTag(DownloadsPresenter.class);

    private Disposable
            getDownloadedPhotosDisp,
            updatePhotoDownloadDisp,
            downloadFilePathDisp;

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
                .flatMap(this::check, this::getPair)
                .flatMap(this::update)
                .toList()
                .flatMapObservable(item4 -> getDataManager().getPhotoDownloads())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        photoDownloads -> {

                            Log.i(TAG,
                                    "getPhotoDownloads:accept:photosNo="
                                            +photoDownloads.size());
                            getView().displayPhotos(photoDownloads);
                        },
                        throwable -> {

                            throwable.printStackTrace();
                            getView().showError();
                        },
                        () -> getView().hideLoading(),
                        disposable -> getView().showLoading());
    }

    private Observable<PhotoDownload.Status> check(PhotoDownload photoDownload) {

        return Observable.create(e -> {
            try {
                e.onNext(
                        getDataManager()
                                .checkDownloadStatus(photoDownload.getDownloadReference()));
                e.onComplete();
            } catch (Exception ex) {
                ex.printStackTrace();
                e.tryOnError(ex);
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
    public void onDownloadComplete(long downloadReference) {

        updatePhotoDownloadDisp = getDataManager()
                .getPhotoDownloadByDownloadReference(downloadReference)
                .flatMap(this::check, this::getPair)
                .flatMap(this::update, this::getPair)
                .filter(item -> item.second)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> getView().updatePhotoDownload(item.first));
    }

    @Override
    public void onOpenFileClick(long downloadReference) {

        downloadFilePathDisp =
                downloadFilePath(downloadReference)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                item -> {

                                    Log.i(TAG,
                                            "getFilePath:accept:path="
                                                    +item);
                                    getView().sendFileIntent(item);
                                },
                                Throwable::printStackTrace);
    }

    private Observable<Uri> downloadFilePath(long downloadReference) {

        return Observable.create(e -> {
            try {
                e.onNext(
                        getDataManager()
                                .getDownloadedFilePath(downloadReference));
                e.onComplete();
            } catch (Exception ex) {
                ex.printStackTrace();
                e.tryOnError(ex);
            }
        });
    }

    private boolean check(long downloadReference, PhotoDownload photoDownload) {
        return photoDownload.getDownloadReference() == downloadReference;
    }

    private Pair<PhotoDownload, Boolean> getPair(Pair<PhotoDownload, PhotoDownload.Status> pair,
                                                 boolean updated) {
        return new Pair<>(pair.first, updated);
    }

    @Override
    public void detachView() {
        super.detachView();

        getDownloadedPhotosDisp.dispose();

        if (updatePhotoDownloadDisp != null) {
            if (!updatePhotoDownloadDisp.isDisposed()) {
                updatePhotoDownloadDisp.dispose();
            }
        }

        if (downloadFilePathDisp != null) {
            if (!downloadFilePathDisp.isDisposed()) {
                downloadFilePathDisp.dispose();
            }
        }
    }
}
