package com.sonu.app.splash.ui.search.activity;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.SearchCollectionsCache;
import com.sonu.app.splash.data.cache.SearchPhotosCache;
import com.sonu.app.splash.data.cache.SearchUsersCache;
import com.sonu.app.splash.data.download.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 03/02/18.
 */

public class SearchPresenter
        extends BasePresenterImpl<SearchContract.View>
        implements SearchContract.Presenter {

    @Inject
    public SearchPresenter(AppBus appBus,
                           DataManager dataManager,
                           Activity activity) {
        super(appBus, dataManager, activity);
    }

    @Override
    public void attachView(SearchContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        if (wasViewRecreated) {

            updateAppropriateUi(getView().getQuery());
        }
    }

    private void updateAppropriateUi(String query) {

        switch (getView().getSearchType()) {

            case SearchActivity.TYPE_PHOTOS:
                SearchPhotosCache searchPhotosCache = getDataManager().getSearchPhotosCache();
                searchPhotosCache.setQuery(query);
                getView().updateUi(searchPhotosCache);
                break;
            case SearchActivity.TYPE_COLLECTIONS:
                SearchCollectionsCache searchCollectionsCache = getDataManager().getSearchCollectionsCache();
                searchCollectionsCache.setQuery(query);
                getView().updateUi(searchCollectionsCache);
                break;
            case SearchActivity.TYPE_USERS:
                SearchUsersCache searchUsersCache = getDataManager().getSearchUsersCache();
                searchUsersCache.setQuery(query);
                getView().updateUi(searchUsersCache);
                break;
        }
    }



    @Override
    public void downloadPhoto(PhotoDownload photoDownload) {

        getDataManager().downloadPhoto(photoDownload);
    }

    @Override
    public void onQuerySubmit(String query) {
        updateAppropriateUi(query);
    }
}
