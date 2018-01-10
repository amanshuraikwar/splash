package com.sonu.app.splash.ui.downloadinfo;

import android.app.Activity;
import android.content.Intent;
import android.util.Pair;

import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.download.PhotoDownloadService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by amanshuraikwar on 24/12/17.
 */

public class DownloadInfoPresenter
        extends BasePresenterImpl<DownloadInfoContract.View>
        implements DownloadInfoContract.Presenter {

    private Disposable downloadStateChangedDsp, downloadProgressDsp;

    @Inject
    public DownloadInfoPresenter(AppBus appBus, DataManager dataManager, Activity activity) {
        super(appBus, dataManager, activity);
    }

    @Override
    public void attachView(DownloadInfoContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        if (wasViewRecreated) {
            downloadStateChangedDsp =
                    getAppBus()
                            .onDownloadStateChange
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                new Consumer<Integer>() {
                                    @Override
                                    public void accept(
                                            Integer t)
                                            throws Exception {
                                        PhotoDownloadService.DownloadState downloadState =
                                                getDataManager().getDownloadSession().getDownloadState();
                                        switch (downloadState.state) {
                                            case IDLE:
                                                onIdle(downloadState);
                                                break;
                                            case WAITING:
                                                onWaiting(downloadState);
                                                break;
                                            case DOWNLOADING:
                                                onDownloading(downloadState);
                                                break;
                                        }
                                    }
                                }
                            );

            downloadProgressDsp =
                    getAppBus()
                            .updateDownloadProgress
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    new Consumer<Pair<Long, Long>>() {
                                        @Override
                                        public void accept(
                                                Pair<Long, Long> t)
                                                throws Exception {
                                            getView().showProgress(t.first, t.second);
                                        }
                                    }
                            );

        }
    }

    @Override
    public void detachView() {
        super.detachView();

        downloadStateChangedDsp.dispose();
    }

    private void onIdle(PhotoDownloadService.DownloadState downloadState) {
        // do nothing
    }

    private void onWaiting(PhotoDownloadService.DownloadState downloadState) {
        getView().showError(
                downloadState.curDownloadFileName,
                downloadState.curDownloadProgress,
                downloadState.downloadQueueLength,
                downloadState.curDownloadPhotoId,
                downloadState.error);
    }

    private void onDownloading(PhotoDownloadService.DownloadState downloadState) {
        getView().updateUi(
                downloadState.curDownloadFileName,
                downloadState.curDownloadProgress,
                downloadState.downloadQueueLength,
                downloadState.curDownloadPhotoId);
    }

    @Override
    public void onCancelClick(String photoId) {

        Intent intent = new Intent(getActivity(), PhotoDownloadService.class);

        // setting action
        intent
                .putExtra(
                        PhotoDownloadService.KEY_ACTION,
                        PhotoDownloadService.ACTION_CANCEL_DOWNLOAD);

        // converting Photo --> PhotoDownload and sending to PhotoDownloadService
        intent
                .putExtra(
                        PhotoDownloadService.KEY_PHOTO_ID,
                        photoId);

        getActivity().startService(intent);
    }

    @Override
    public void onRetryClick(String photoId) {

        Intent intent = new Intent(getActivity(), PhotoDownloadService.class);

        // setting action
        intent
                .putExtra(
                        PhotoDownloadService.KEY_ACTION,
                        PhotoDownloadService.ACTION_RETRY_CURRENT_DOWNLOAD);

        // converting Photo --> PhotoDownload and sending to PhotoDownloadService
        intent
                .putExtra(
                        PhotoDownloadService.KEY_PHOTO_ID,
                        photoId);

        getActivity().startService(intent);
    }
}
