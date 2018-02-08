package com.sonu.app.splash.ui.content.allphotos;

import android.app.Activity;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.ui.architecture.PresenterPlugin;
import com.sonu.app.splash.ui.content.ContentContract;
import com.sonu.app.splash.ui.content.ContentPresenter;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class AllPhotosContract {

    public interface View extends ContentContract.View {

    }

    public interface Presenter extends ContentContract.Presenter<View> {

        void downloadPhoto(Photo photo);
    }

    public static class PresenterImpl extends ContentPresenter<View> implements Presenter {

        private Disposable downloadPhotoDisp;

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
            return getDataManager().getAllPhotosCache();
        }

        @Override
        public void downloadPhoto(Photo photo) {

            downloadPhotoDisp = PresenterPlugin.DownloadPhoto.downloadPhoto(photo, this);
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
}
