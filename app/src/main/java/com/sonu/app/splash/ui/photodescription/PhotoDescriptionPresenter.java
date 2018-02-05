package com.sonu.app.splash.ui.photodescription;

import android.app.Activity;
import android.util.Log;

import com.sonu.app.splash.R;
import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.download.PhotoDownload;
import com.sonu.app.splash.data.network.unsplashapi.UnsplashApiException;
import com.sonu.app.splash.ui.about.AboutContract;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.util.LogUtils;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
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

    private Disposable photoDescriptionDesc;
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
        }
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
                            new Consumer<PhotoDescription>() {
                                @Override
                                public void accept(PhotoDescription photoDescription) throws Exception {
                                    Log.d(TAG, "getPhotoDescription:onNext:called");
                                    getView().displayPhotoDescription(photoDescription);
                                }
                            },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Log.d(TAG, "getPhotoDescription:onError:called");
                                    Log.e(TAG, "getPhotoDescription:onError:error=" + throwable);
                                    throwable.printStackTrace();
                                    handleException(throwable);
                                    fetchingData = false;
                                }
                            },
                            new Action() {
                                @Override
                                public void run() throws Exception {
                                    Log.d(TAG, "getPhotoDescription:onCompleted:called");
                                    getView().hideLoading();
                                    fetchingData = false;
                                }
                            }
                            ,
                            new Consumer<Disposable>() {
                                @Override
                                public void accept(Disposable disposable) throws Exception {
                                    Log.d(TAG, "getPhotoDescription:onSubscribe:called");
                                    getView().showLoading();
                                }
                            });
        }
    }

    @Override
    public void downloadPhoto(PhotoDownload photoDownload) {
        getDataManager().downloadPhoto(photoDownload);
    }

    @Override
    public void detachView() {
        super.detachView();

        photoDescriptionDesc.dispose();
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
}
