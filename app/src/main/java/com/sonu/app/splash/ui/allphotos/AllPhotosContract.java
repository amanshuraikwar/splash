package com.sonu.app.splash.ui.allphotos;

import com.sonu.app.splash.data.download.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.photo.Photo;

import java.util.List;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

public interface AllPhotosContract {

    interface View extends BaseView {

        int getHomeNavItemId();
        void displayPhotos(List<ListItem> photoListItems);
        void addPhotos(List<ListItem> photoListItems);
        boolean isListEmpty();
        void showLoading();
        void hideLoading();
        void showIoException(int titleStringRes, int messageStringRes);
        void showUnsplashApiException(int titleStringRes, int messageStringRes);
        void showUnknownException(String message);
        void downloadPhoto(PhotoDownload photoDownload);
        boolean checkPermissions();
        void startPhotoDescriptionActivity(Photo photo);
    }

    interface Presenter extends BasePresenter<View> {
        void getMorePhotos();
        void getAllPhotos();
    }
}
