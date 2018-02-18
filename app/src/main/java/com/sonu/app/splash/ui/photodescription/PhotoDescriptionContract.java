package com.sonu.app.splash.ui.photodescription;

import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;

/**
 * Created by amanshuraikwar on 12/01/18.
 */

public interface PhotoDescriptionContract {

    interface View extends BaseView {
        String getCurPhotoId();
        Photo getCurPhoto();
        void displayPhotoDescription(Photo photo);
        void showError();
        void hideLoading();
        void showLoading();
        void setFavActive();
        void setFavInactive();
    }

    interface Presenter extends BasePresenter<View> {
        void getData();
        void downloadPhoto(Photo photo);
        void onAddToFavClick();
    }
}
