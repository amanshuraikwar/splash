package com.sonu.app.splash.ui.photostats;

import android.app.Activity;
import android.util.Log;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.UiExceptionUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amanshuraikwar on 13/02/18.
 */

public class PhotoStatsPresenter
        extends BasePresenterImpl<PhotoStatsContract.View>
        implements PhotoStatsContract.Presenter {

    private static final String TAG = LogUtils.getLogTag(PhotoStatsPresenter.class);
    private Disposable photoStatsDisp;
    private boolean fetchingData;

    @Inject
    public PhotoStatsPresenter(AppBus appBus, DataManager dataManager, Activity activity) {
        super(appBus, dataManager, activity);
    }

    @Override
    public void attachView(PhotoStatsContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        if (wasViewRecreated) {

            getData();
        }
    }

    @Override
    public void getData() {

        if (fetchingData) {
            return;
        }

        photoStatsDisp =
                getDataManager()
                        .getPhotoStats(getView().getPhotoId())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            photoStats -> {
                                Log.d(TAG, "getData:onNext:called");
                                getView().updateUi(photoStats);
                            },
                            throwable -> {
                                Log.d(TAG, "getData:onError:called");
                                Log.e(TAG, "getData:onError:error=" + throwable);
                                throwable.printStackTrace();
                                getView().showError();
                                fetchingData = false;
                            },
                            () -> {
                                Log.d(TAG, "getData:onCompleted:called");
                                getView().hideLoading();
                                fetchingData = false;
                            },
                            disposable -> {
                                Log.d(TAG, "getData:onSubscribe:called");
                                getView().showLoading();
                                fetchingData = true;
                            });
    }

    @Override
    public void detachView() {
        super.detachView();

        if (photoStatsDisp != null) {
            photoStatsDisp.dispose();
        }
    }
}
