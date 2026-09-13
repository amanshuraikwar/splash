package com.sonu.app.splash.ui.content.searchphotos;

import android.app.Activity;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.AppDataStore;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.data.cache.SearchCache;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.PresenterPlugin;
import com.sonu.app.splash.ui.content.ContentContract;
import com.sonu.app.splash.ui.content.ContentPresenter;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 12/02/18.
 */

public class SearchPhotosContract {

    public interface View extends ContentContract.View {

        String getCurSearchQuery();
    }

    public interface Presenter extends ContentContract.Presenter<View> {

        void onSearchQueryChanged();
        void downloadPhoto(Photo photo);
    }

    public static class PresenterImpl extends ContentPresenter<View> implements Presenter {

        private SearchCache searchCache;

        @Inject
        public PresenterImpl(AppBus appBus, AppDataStore appDataStore, Activity activity) {
            super(appBus, appDataStore, activity);
        }

        @Override
        public String getTag() {
            return LogUtils.getLogTag(PresenterImpl.class);
        }

        @Override
        public ContentCache getContentCache() {
            if (searchCache == null) {
                searchCache = getAppDataStore().getSearchPhotosCache();
            }

            if (!searchCache.getQuery().equals(getView().getCurSearchQuery())) {
                searchCache.setQuery(getView().getCurSearchQuery());
            }

            return searchCache;
        }

        @Override
        public void onSearchQueryChanged() {
            resetList();
        }

        @Override
        public void downloadPhoto(Photo photo) {
            PresenterPlugin.DownloadPhoto.downloadPhoto(photo, this);
        }

        @Override
        public void detachView() {
            super.detachView();

        }
    }
}
