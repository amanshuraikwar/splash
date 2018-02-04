package com.sonu.app.splash.ui.search.activity;

import com.sonu.app.splash.data.cache.PhotosCache;
import com.sonu.app.splash.data.cache.SearchCache;
import com.sonu.app.splash.data.cache.SearchCollectionsCache;
import com.sonu.app.splash.data.cache.SearchPhotosCache;
import com.sonu.app.splash.data.cache.SearchUsersCache;
import com.sonu.app.splash.data.download.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.ui.architecture.BaseView;

/**
 * Created by amanshuraikwar on 03/02/18.
 */

public interface SearchContract {

    interface View extends BaseView {

        String getSearchType();
        void updateUi(SearchPhotosCache cache);
        void updateUi(SearchCollectionsCache cache);
        void updateUi(SearchUsersCache cache);
        String getQuery();
    }

    interface Presenter extends BasePresenter<View> {

        void downloadPhoto(PhotoDownload photoDownload);
        void onQuerySubmit(String query);
    }
}
