package com.sonu.app.splash.ui.photostats;

import com.sonu.app.splash.model.unsplash.PhotoStats;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.ui.architecture.BaseView;

/**
 * Created by amanshuraikwar on 13/02/18.
 */

public interface PhotoStatsContract {

    interface View extends BaseView {
        void updateUi(PhotoStats photoStats);
        String getPhotoId();
        void showLoading();
        void hideLoading();
        void showError();
    }

    interface Presenter extends BasePresenter<View> {
        void getData();
    }
}
