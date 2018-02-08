package com.sonu.app.splash.ui.search.activity;

import android.app.Activity;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.SearchCollectionsCache;
import com.sonu.app.splash.data.cache.SearchPhotosCache;
import com.sonu.app.splash.data.cache.SearchUsersCache;
import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.ui.architecture.PresenterPlugin;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by amanshuraikwar on 03/02/18.
 */

public class SearchPresenter
        extends BasePresenterImpl<SearchContract.View>
        implements SearchContract.Presenter {

    private Disposable downloadPhotoDisp;

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
    public void downloadPhoto(Photo photo) {

        downloadPhotoDisp = PresenterPlugin.DownloadPhoto.downloadPhoto(photo, this);
    }

    @Override
    public void onQuerySubmit(String query) {
        updateAppropriateUi(query);
    }

    @Override
    public void detachView() {
        super.detachView();

        if (downloadPhotoDisp != null) {
            if (!downloadPhotoDisp.isDisposed()) {
                downloadPhotoDisp.dispose();
            }
        }
    }
}
