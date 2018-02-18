package com.sonu.app.splash.ui.content.usercollections;

import android.app.Activity;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.PresenterPlugin;
import com.sonu.app.splash.ui.content.ContentContract;
import com.sonu.app.splash.ui.content.ContentPresenter;
import com.sonu.app.splash.ui.content.curatedphotos.CuratedPhotosContract;
import com.sonu.app.splash.ui.content.userphotos.UserPhotosContract;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by amanshuraikwar on 12/02/18.
 */

public class UserCollectionsContract {

    public interface View extends ContentContract.View {
        String getUsername();
    }

    public interface Presenter extends ContentContract.Presenter<View> {

        void onUsernameChanged();
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
            return LogUtils.getLogTag(PresenterImpl.class);
        }

        @Override
        public ContentCache getContentCache() {
            return getDataManager().getUserCollectionsCache(getView().getUsername());
        }

        @Override
        public void onUsernameChanged() {
            resetList();
        }

    }
}
