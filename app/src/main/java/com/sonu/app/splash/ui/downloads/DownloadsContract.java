package com.sonu.app.splash.ui.downloads;

import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;
import com.sonu.app.splash.model.unsplash.Photo;

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
    }

    interface Presenter extends BasePresenter<View> {

        void getPhotos();
    }
}
