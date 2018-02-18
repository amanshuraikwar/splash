package com.sonu.app.splash.ui.content.searchusers;

import android.app.Activity;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.data.cache.SearchCache;
import com.sonu.app.splash.ui.content.ContentContract;
import com.sonu.app.splash.ui.content.ContentPresenter;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 12/02/18.
 */

public class SearchUsersContract {

    public interface View extends ContentContract.View {

        String getCurSearchQuery();
    }

    public interface Presenter extends ContentContract.Presenter<View> {

        void onSearchQueryChanged();
    }

    public static class PresenterImpl extends ContentPresenter<View> implements Presenter {

        private SearchCache searchCache;

        @Inject
        public PresenterImpl(AppBus appBus, DataManager dataManager, Activity activity) {
            super(appBus, dataManager, activity);
        }

        @Override
        public String getTag() {
            return LogUtils.getLogTag(PresenterImpl.class);
        }

        @Override
        public ContentCache getContentCache() {
            if (searchCache == null) {
                searchCache = getDataManager().getSearchUsersCache();
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
    }
}
