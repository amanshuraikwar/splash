package com.sonu.app.splash.ui.downloads;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;

import com.sonu.app.splash.data.local.room.photodownload.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.AppDataStore;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 24/12/17.
 */

public class DownloadsPresenter
        extends BasePresenterImpl<DownloadsContract.View>
        implements DownloadsContract.Presenter {

    private static final String TAG = LogUtils.getLogTag(DownloadsPresenter.class);

    @Inject
    public DownloadsPresenter(AppBus appBus, AppDataStore appDataStore, Activity activity) {
        super(appBus, appDataStore, activity);
    }

    @Override
    public void attachView(DownloadsContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        if (wasViewRecreated) {

            runDownloadStatusTask();
        }
    }

    private void runDownloadStatusTask() {

        getView().showLoading();
        runInBackground(
                () -> {
                    for (PhotoDownload photoDownload :
                            getAppDataStore().getRunningPausedPendingDownloads()) {
                        photoDownload.setStatus(
                                getAppDataStore().checkDownloadStatus(
                                        photoDownload.getDownloadReference()));
                        getAppDataStore().updatePhotoDownload(photoDownload);
                    }
                    return getAppDataStore().getPhotoDownloads();
                },
                photoDownloads -> {
                    Log.i(TAG, "getPhotoDownloads:count=" + photoDownloads.size());
                    getView().displayPhotos(photoDownloads);
                    getView().hideLoading();
                },
                throwable -> getView().showError());
    }

    @Override
    public void getPhotos() {

        runDownloadStatusTask();
    }

    @Override
    public void onDownloadComplete(long downloadReference) {

        runInBackground(
                () -> {
                    PhotoDownload photoDownload = getAppDataStore()
                            .getPhotoDownloadByDownloadReference(downloadReference);
                    if (photoDownload == null) {
                        return null;
                    }
                    photoDownload.setStatus(
                            getAppDataStore().checkDownloadStatus(downloadReference));
                    return getAppDataStore().updatePhotoDownload(photoDownload)
                            ? photoDownload
                            : null;
                },
                photoDownload -> {
                    if (photoDownload != null) {
                        getView().updatePhotoDownload(photoDownload);
                    }
                },
                Throwable::printStackTrace);
    }

    @Override
    public void onOpenFileClick(long downloadReference) {

        runInBackground(
                () -> getAppDataStore().getDownloadedFilePath(downloadReference),
                item -> {
                    Log.i(TAG, "getFilePath:path=" + item);
                    getView().sendFileIntent(item);
                },
                Throwable::printStackTrace);
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

    }
}
