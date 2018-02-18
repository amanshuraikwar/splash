package com.sonu.app.splash.ui.userdescription;

import android.app.Activity;
import android.util.Log;

import com.sonu.app.splash.R;
import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.UserPhotosCache;
import com.sonu.app.splash.data.local.room.favourites.FavCollection;
import com.sonu.app.splash.data.local.room.favourites.FavUser;
import com.sonu.app.splash.data.network.unsplashapi.UnsplashApiException;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.PresenterPlugin;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.NumberUtils;
import com.sonu.app.splash.util.UiExceptionUtils;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amanshuraikwar on 16/01/18.
 */

public class UserDescriptionPresenter
        extends BasePresenterImpl<UserDescriptionContract.View>
        implements UserDescriptionContract.Presenter {

    private static final String TAG = LogUtils.getLogTag(UserDescriptionPresenter.class);

    private Disposable userDescriptionDisp, favUserDisp, bookmarkDisp;
    private boolean fetchingData;

    @Inject
    public UserDescriptionPresenter(AppBus appBus, DataManager dataManager, Activity activity) {
        super(appBus, dataManager, activity);
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

        bookmarkDisp = getDataManager()
                .isUserFav(getView().getCurArtistId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    if (value) {
                        getView().setFavActive();
                    } else {
                        getView().setFavInactive();
                    }
                });
    }

    @Override
    public synchronized void getData() {

        Log.d(TAG, "getData:called");
        Log.i(TAG, "getData:locked="+fetchingData);


        if (!fetchingData) {
            fetchingData = true;

            userDescriptionDisp = getDataManager()
                    .getUserDescription(getView().getCurArtistUsername())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            user -> {
                                Log.d(TAG, "getUser:onNext:called");
                                getView().displayUserDescription(user);
                            },
                            throwable -> {
                                Log.d(TAG, "getUser:onError:called");
                                Log.e(TAG, "getUser:onError:error=" + throwable);
                                throwable.printStackTrace();
                                getView().showError();
                                fetchingData = false;
                            },
                            () -> {
                                Log.d(TAG, "getUser:onCompleted:called");
                                getView().hideLoading();
                                fetchingData = false;
                            },
                            disposable -> {
                                Log.d(TAG, "getUser:onSubscribe:called");
                                getView().showLoading();
                            });
        }

    }

    @Override
    public void onAddToFavClick() {

        favUserDisp =
                getDataManager()
                        .isUserFav(getView().getCurArtistId())
                        .flatMap(this::getFavObs)
                        .flatMap(temp ->
                                getDataManager().isUserFav(
                                        getView().getCurArtistId()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                isFav -> {

                                    if (isFav) {

                                        getView().setFavActive();
                                    } else {

                                        getView().setFavInactive();
                                    }
                                }
                        );
    }

    private Observable<Boolean> getFavObs(Boolean isFav) {

        if (isFav) {

            return getDataManager()
                    .getFavUserById(getView().getCurArtistId())
                    .flatMap(getDataManager()::removeFav)
                    .filter(success -> success);
        } else {

            return getDataManager()
                    .addFav(new FavUser(getView().getCurUser(), NumberUtils.getCurrentDate()))
                    .filter(success -> success);
        }
    }

    @Override
    public void detachView() {
        super.detachView();

        userDescriptionDisp.dispose();

        if(favUserDisp != null) {
            if (!favUserDisp.isDisposed()) {
                favUserDisp.dispose();
            }
        }

        bookmarkDisp.dispose();
    }
}
