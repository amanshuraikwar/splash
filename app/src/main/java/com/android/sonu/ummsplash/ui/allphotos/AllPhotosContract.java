package com.android.sonu.ummsplash.ui.allphotos;

import com.android.sonu.ummsplash.data.download.PhotoDownload;
import com.android.sonu.ummsplash.ui.architecture.BasePresenter;
import com.android.sonu.ummsplash.ui.architecture.BaseView;
import com.android.sonu.ummsplash.ui.list.ListItem;

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
    }

    interface Presenter extends BasePresenter<View> {
        void getMorePhotos();
        void getAllPhotos();
    }
}
