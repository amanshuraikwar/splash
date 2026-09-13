package com.sonu.app.splash.ui.userdescription;

import android.app.Activity;
import android.util.Log;

import com.sonu.app.splash.R;
import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.AppDataStore;
import com.sonu.app.splash.data.cache.UserPhotosCache;
import com.sonu.app.splash.data.local.room.favourites.FavCollection;
import com.sonu.app.splash.data.local.room.favourites.FavUser;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.PresenterPlugin;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.NumberUtils;
import com.sonu.app.splash.util.UiExceptionUtils;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 16/01/18.
 */

public class UserDescriptionPresenter
        extends BasePresenterImpl<UserDescriptionContract.View>
        implements UserDescriptionContract.Presenter {

    private static final String TAG = LogUtils.getLogTag(UserDescriptionPresenter.class);

    private boolean fetchingData;

    @Inject
    public UserDescriptionPresenter(AppBus appBus, AppDataStore appDataStore, Activity activity) {
        super(appBus, appDataStore, activity);
    }

    @Override
    public void attachView(UserDescriptionContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        if (wasViewRecreated) {

            getData();

            checkForBookmark();
        }
    }

    private void checkForBookmark() {

        runInBackground(
                () -> getAppDataStore().isUserFav(getView().getCurArtistId()),
                value -> {
                    if (value) {
                        getView().setFavActive();
                    } else {
                        getView().setFavInactive();
                    }
                },
                throwable -> getView().setFavInactive());
    }

    @Override
    public synchronized void getData() {

        Log.d(TAG, "getData:called");
        Log.i(TAG, "getData:locked="+fetchingData);


        if (!fetchingData) {
            fetchingData = true;

            getView().showLoading();
            runInBackground(
                    () -> getAppDataStore().getUserDescription(
                            getView().getCurArtistUsername()),
                    user -> {
                        Log.d(TAG, "getUser:completed");
                        getView().displayUserDescription(user);
                        getView().hideLoading();
                        fetchingData = false;
                    },
                    throwable -> {
                        Log.e(TAG, "getUser:error=" + throwable);
                        getView().showError();
                        fetchingData = false;
                    });
        }

    }

    @Override
    public void onAddToFavClick() {

        runInBackground(
                () -> {
                    boolean isFavorite = getAppDataStore().isUserFav(getView().getCurArtistId());
                    if (isFavorite) {
                        FavUser favorite = getAppDataStore()
                                .getFavUserById(getView().getCurArtistId());
                        if (favorite != null) {
                            getAppDataStore().removeFav(favorite);
                        }
                    } else {
                        getAppDataStore().addFav(
                                new FavUser(getView().getCurUser(), NumberUtils.getCurrentDate()));
                    }
                    return getAppDataStore().isUserFav(getView().getCurArtistId());
                },
                isFavorite -> {
                    if (isFavorite) {
                        getView().setFavActive();
                    } else {
                        getView().setFavInactive();
                    }
                },
                throwable -> getView().setFavInactive());
    }

    @Override
    public void detachView() {
        super.detachView();

    }
}
