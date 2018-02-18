package com.sonu.app.splash.ui.photodescription;

import android.app.Activity;
import android.util.Log;

import com.sonu.app.splash.R;
import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.local.room.favourites.FavPhoto;
import com.sonu.app.splash.data.network.unsplashapi.UnsplashApiException;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.ui.architecture.PresenterPlugin;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.NumberUtils;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

public class PhotoDescriptionPresenter extends BasePresenterImpl<PhotoDescriptionContract.View>
        implements PhotoDescriptionContract.Presenter {

    private static final String TAG = LogUtils.getLogTag(PhotoDescriptionPresenter.class);

    private Disposable photoDescriptionDesc, downloadPhotoDisp, favPhotoDisp, bookmarkDisp;
    private boolean fetchingData;

    @Inject
    public PhotoDescriptionPresenter(AppBus appBus, DataManager dataManager, Activity activity) {
        super(appBus, dataManager, activity);
    }

    @Override
    public void attachView(PhotoDescriptionContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        if (wasViewRecreated) {

            getData();

            checkForBookmark();
        }
    }

    private void checkForBookmark() {

        bookmarkDisp = getDataManager()
                .isPhotoFav(getView().getCurPhotoId())
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

            photoDescriptionDesc = getDataManager()
                    .getPhotoDescription(getView().getCurPhotoId())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            photo -> {
                                Log.d(TAG, "getPhotoDescription:onNext:called");
                                getView().displayPhotoDescription(photo);
                            },
                            throwable -> {
                                Log.d(TAG, "getPhotoDescription:onError:called");
                                Log.e(TAG, "getPhotoDescription:onError:error=" + throwable);
                                throwable.printStackTrace();
                                getView().showError();
                                fetchingData = false;
                            },
                            () -> {
                                Log.d(TAG, "getPhotoDescription:onCompleted:called");
                                getView().hideLoading();
                                fetchingData = false;
                            },
                            disposable -> {
                                Log.d(TAG, "getPhotoDescription:onSubscribe:called");
                                getView().showLoading();
                            });
        }
    }

    @Override
    public void downloadPhoto(Photo photo) {
        downloadPhotoDisp = PresenterPlugin.DownloadPhoto.downloadPhoto(photo, this);
    }

    @Override
    public void onAddToFavClick() {

        favPhotoDisp =
                getDataManager()
                        .isPhotoFav(getView().getCurPhotoId())
                        .flatMap(this::getFavObs)
                        .flatMap(temp -> getDataManager().isPhotoFav(getView().getCurPhotoId()))
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
                    .getFavPhotoById(getView().getCurPhotoId())
                    .flatMap(getDataManager()::removeFav)
                    .filter(success -> success);
        } else {

            return getDataManager()
                    .addFav(new FavPhoto(getView().getCurPhoto(), NumberUtils.getCurrentDate()))
                    .filter(success -> success);
        }
    }

    @Override
    public void detachView() {
        super.detachView();

        photoDescriptionDesc.dispose();

        if (downloadPhotoDisp != null) {
            if (!downloadPhotoDisp.isDisposed()) {
                downloadPhotoDisp.dispose();
            }
        }

        if (favPhotoDisp != null) {
            if (!favPhotoDisp.isDisposed()) {
                favPhotoDisp.dispose();
            }
        }

        bookmarkDisp.dispose();
    }
}
