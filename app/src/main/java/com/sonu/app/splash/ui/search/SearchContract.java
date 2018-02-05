package com.sonu.app.splash.ui.search;

import com.sonu.app.splash.data.cache.PhotosCache;
import com.sonu.app.splash.data.cache.SearchCollectionsCache;
import com.sonu.app.splash.data.cache.SearchPhotosCache;
import com.sonu.app.splash.data.cache.SearchUsersCache;
import com.sonu.app.splash.data.download.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;
import com.sonu.app.splash.ui.collection.Collection;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.ui.userdescription.UserDescription;

import java.util.List;

/**
 * Created by amanshuraikwar on 02/02/18.
 */

public interface SearchContract {

    interface View extends BaseView {

        void updatePhotos(SearchPhotosCache searchPhotosCache);
        void updateCollections(SearchCollectionsCache searchCollectionsCache);
        void updateUsers(SearchUsersCache searchUsersCache);
        String getInitialQuery();
    }

    interface Presenter extends BasePresenter<View> {

        void onSearchClick(String query);
        void downloadPhoto(PhotoDownload photoDownload);
    }
}
