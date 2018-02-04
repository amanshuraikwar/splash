package com.sonu.app.splash.ui.content.allcollections;

import android.app.Activity;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.ui.content.ContentContract;
import com.sonu.app.splash.ui.content.ContentPresenter;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class AllCollectionsContract {

    public interface View extends ContentContract.View {

    }

    public interface Presenter extends ContentContract.Presenter<View> {

    }

    public static class PresenterImpl extends ContentPresenter<View> implements Presenter {

        @Inject
        public PresenterImpl(AppBus appBus,
                             DataManager dataManager,
                             Activity activity) {
            super(appBus, dataManager, activity);
        }

        @Override
        public String getTag() {
            return LogUtils.getLogTag(AllCollectionsContract.PresenterImpl.class);
        }

        @Override
        public ContentCache getContentCache() {
            return getDataManager().getAllCollectionsCache();
        }
    }
}
