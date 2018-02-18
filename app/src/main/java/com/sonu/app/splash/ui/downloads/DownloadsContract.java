package com.sonu.app.splash.ui.downloads;

import android.net.Uri;

import com.sonu.app.splash.data.local.room.photodownload.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;

import java.util.List;

/**
 * Created by amanshuraikwar on 24/12/17.
 */

public interface DownloadsContract {

    interface View extends BaseView {

        void displayPhotos(List<PhotoDownload> photoDownloads);
        void showLoading();
        void hideLoading();
        void showError();
        List<PhotoDownload> getPhotoDownloadsOnScreen();
        void updatePhotoDownload(PhotoDownload photoDownload);
        void sendFileIntent(Uri uri);
    }

    interface Presenter extends BasePresenter<View> {

        void getPhotos();
        void onDownloadComplete(long downloadReference);
        void onOpenFileClick(long downloadReference);
    }
}
