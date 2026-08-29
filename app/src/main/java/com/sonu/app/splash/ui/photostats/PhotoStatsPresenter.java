package com.sonu.app.splash.ui.photostats;

import android.app.Activity;
import android.util.Log;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.AppDataStore;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.UiExceptionUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 13/02/18.
 */

public class PhotoStatsPresenter
        extends BasePresenterImpl<PhotoStatsContract.View>
        implements PhotoStatsContract.Presenter {

    private static final String TAG = LogUtils.getLogTag(PhotoStatsPresenter.class);
    private boolean fetchingData;

    @Inject
    public PhotoStatsPresenter(AppBus appBus, AppDataStore appDataStore, Activity activity) {
        super(appBus, appDataStore, activity);
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

        fetchingData = true;
        getView().showLoading();
        runInBackground(
                () -> getAppDataStore().getPhotoStats(getView().getPhotoId()),
                photoStats -> {
                    Log.d(TAG, "getData:completed");
                    getView().updateUi(photoStats);
                    getView().hideLoading();
                    fetchingData = false;
                },
                throwable -> {
                    Log.e(TAG, "getData:error=" + throwable);
                    getView().showError();
                    fetchingData = false;
                });
    }

    @Override
    public void detachView() {
        super.detachView();

    }
}
