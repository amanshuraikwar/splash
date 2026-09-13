package com.sonu.app.splash.ui.content.collectionphotos;

import android.app.Activity;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.AppDataStore;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.PresenterPlugin;
import com.sonu.app.splash.ui.content.ContentContract;
import com.sonu.app.splash.ui.content.ContentPresenter;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class CollectionPhotosContract {

    public interface View extends ContentContract.View {
        String getCollectionId();
    }

    public interface Presenter extends ContentContract.Presenter<View> {

        void downloadPhoto(Photo photo);
    }

    public static class PresenterImpl extends ContentPresenter<View> implements Presenter {

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
            return getAppDataStore().getCollectionPhotosCache(getView().getCollectionId());
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
