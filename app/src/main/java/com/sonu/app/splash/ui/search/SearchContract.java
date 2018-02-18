package com.sonu.app.splash.ui.search;

import com.sonu.app.splash.data.cache.SearchCollectionsCache;
import com.sonu.app.splash.data.cache.SearchPhotosCache;
import com.sonu.app.splash.data.cache.SearchUsersCache;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;

/**
 * Created by amanshuraikwar on 02/02/18.
 */

public interface SearchContract {

    interface View extends BaseView {

        String getInitialQuery();
        void initViewPager(String query);
        void setQuery(String query);
        boolean isFirstQuery();
    }

    interface Presenter extends BasePresenter<View> {

        void onSearchClick(String query);
    }
}
