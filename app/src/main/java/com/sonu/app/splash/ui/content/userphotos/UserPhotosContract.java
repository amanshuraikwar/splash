package com.sonu.app.splash.ui.content.userphotos;

import android.app.Activity;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.AppDataStore;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.PresenterPlugin;
import com.sonu.app.splash.ui.content.ContentContract;
import com.sonu.app.splash.ui.content.ContentPresenter;
import com.sonu.app.splash.ui.content.curatedphotos.CuratedPhotosContract;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 12/02/18.
 */

public class UserPhotosContract {

    public interface View extends ContentContract.View {
        String getUsername();
    }

    public interface Presenter extends ContentContract.Presenter<View> {

        void downloadPhoto(Photo photo);
        void onUsernameChanged();
    }

    public static class PresenterImpl extends ContentPresenter<View> implements Presenter {

        @Inject
        public PresenterImpl(AppBus appBus,
                             AppDataStore appDataStore,
                             Activity activity) {
            super(appBus, appDataStore, activity);
        }

        @Override
        public String getTag() {
            return LogUtils.getLogTag(PresenterImpl.class);
        }

        @Override
        public ContentCache getContentCache() {
            return getAppDataStore().getUserPhotosCache(getView().getUsername());
        }

        @Override
        public void downloadPhoto(Photo photo) {

            PresenterPlugin.DownloadPhoto.downloadPhoto(photo, this);
        }

        @Override
        public void onUsernameChanged() {
            resetList();
        }

        @Override
        public void detachView() {
            super.detachView();

        }
    }
}
