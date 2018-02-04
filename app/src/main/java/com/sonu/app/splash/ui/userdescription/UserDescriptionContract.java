package com.sonu.app.splash.ui.userdescription;

import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.data.download.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;
import com.sonu.app.splash.ui.photo.Photo;

/**
 * Created by amanshuraikwar on 16/01/18.
 */

public interface UserDescriptionContract {

    interface View extends BaseView {
        void hideLoading();
        void showLoading();
        void showIoException(int titleStringRes, int messageStringRes);
        void showUnsplashApiException(int titleStringRes, int messageStringRes);
        void showUnknownException(String message);
        void displayUserDescription(UserDescription userDescription);
        String getCurArtistUsername();
        void downloadPhoto(PhotoDownload photoDownload);
        void startPhotoDescriptionActivity(Photo photo);
        void setupList(DataManager dataManager, ContentCache contentCache);
        void getAllPhotos();
        boolean isListEmpty();
    }

    interface Presenter extends BasePresenter<View> {
        void onDownloadBtnClick(Photo photo);
        void onPhotoClick(Photo photo);
        void getData();
    }
}
