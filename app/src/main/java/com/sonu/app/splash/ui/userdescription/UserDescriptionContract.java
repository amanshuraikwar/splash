package com.sonu.app.splash.ui.userdescription;

import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;
import com.sonu.app.splash.model.unsplash.Photo;

/**
 * Created by amanshuraikwar on 16/01/18.
 */

public interface UserDescriptionContract {

    interface View extends BaseView {

        void hideLoading();
        void showLoading();
        void showError();
        void displayUserDescription(User user);
        String getCurArtistUsername();
        String getCurArtistId();
        User getCurUser();
        void setFavActive();
        void setFavInactive();
    }

    interface Presenter extends BasePresenter<View> {

        void getData();
        void onAddToFavClick();
    }
}
