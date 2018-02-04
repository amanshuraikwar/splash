package com.sonu.app.splash.ui.userdescription;

import android.app.Activity;
import android.util.Log;

import com.sonu.app.splash.R;
import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.UserPhotosCache;
import com.sonu.app.splash.data.download.PhotoDownload;
import com.sonu.app.splash.data.network.unsplashapi.UnsplashApiException;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.PermissionsHelper;

import java.io.IOException;

import javax.inject.Inject;

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

    private Disposable userDescriptionDisp;
    private boolean fetchingData;

    private UserPhotosCache userPhotosCache;

    @Inject
    public UserDescriptionPresenter(AppBus appBus, DataManager dataManager, Activity activity) {
        super(appBus, dataManager, activity);
    }

    @Override
    public void attachView(UserDescriptionContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        if (wasViewRecreated) {

            getData();

            if (userPhotosCache == null) {
                userPhotosCache =
                        getDataManager().getUserPhotosCache(getView().getCurArtistUsername());
            }

            getView().setupList(getDataManager(), userPhotosCache);
            getView().getAllPhotos();
        } else {

            if (getView().isListEmpty()) {
                getView().getAllPhotos();
            }
        }
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
                            new Consumer<UserDescription>() {
                                @Override
                                public void accept(UserDescription userDescription) throws Exception {
                                    Log.d(TAG, "getUserDescription:onNext:called");
                                    getView().displayUserDescription(userDescription);
                                }
                            },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Log.d(TAG, "getUserDescription:onError:called");
                                    Log.e(TAG, "getUserDescription:onError:error=" + throwable);
                                    throwable.printStackTrace();
                                    handleException(throwable);
                                    fetchingData = false;
                                }
                            },
                            new Action() {
                                @Override
                                public void run() throws Exception {
                                    Log.d(TAG, "getUserDescription:onCompleted:called");
                                    getView().hideLoading();
                                    fetchingData = false;
                                }
                            },
                            new Consumer<Disposable>() {
                                @Override
                                public void accept(Disposable disposable) throws Exception {
                                    Log.d(TAG, "getUserDescription:onSubscribe:called");
                                    getView().showLoading();
                                }
                            });
        }

    }
    @Override
    public void detachView() {
        super.detachView();

        userDescriptionDisp.dispose();
    }

    private void handleException(Throwable e) {
        if (e instanceof IOException) {

            getView().showIoException(
                    R.string.io_exception_title,
                    R.string.io_exception_message
            );
        } else if (e instanceof UnsplashApiException) {

            if (((UnsplashApiException) e)
                    .getCode() == UnsplashApiException.CODE_API_LIMIT_EXCEEDED) {

                getView().showUnsplashApiException(
                        R.string.unsplash_api_rate_limit_exceed_title,
                        R.string.unsplash_api_rate_limit_exceed_message);
            } else {

                getView().showUnsplashApiException(
                        R.string.unsplash_api_unknown_exception_title,
                        R.string.unsplash_api_unknown_exception_message);
            }
        } else {
            getView().showUnknownException(e.getMessage());
        }
    }

    @Override
    public void onDownloadBtnClick(Photo photo) {
        if (PermissionsHelper.checkStoragePermission(getActivity())) {
            getView().downloadPhoto(new PhotoDownload(photo));
        }
    }

    @Override
    public void onPhotoClick(Photo photo) {
        getView().startPhotoDescriptionActivity(photo);
    }
}
