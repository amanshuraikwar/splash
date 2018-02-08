package com.sonu.app.splash.ui.photodescription;

import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;

/**
 * Created by amanshuraikwar on 12/01/18.
 */

public interface PhotoDescriptionContract {

    interface View extends BaseView {
        void hideLoading();
        void showIoException(int titleStringRes, int messageStringRes);
        void showUnsplashApiException(int titleStringRes, int messageStringRes);
        void showUnknownException(String message);
        void displayPhotoDescription(Photo photo);
        String getCurPhotoId();
        void showLoading();
    }

    interface Presenter extends BasePresenter<View> {
        void getData();
        void downloadPhoto(Photo photo);
    }
}
