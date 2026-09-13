package com.sonu.app.splash.ui.home;

import android.app.Activity;
import android.util.Log;

import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.AppDataStore;
import com.sonu.app.splash.data.download.PhotoDownloadService;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

import com.sonu.app.splash.bus.EventChannel;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class HomePresenter
        extends BasePresenterImpl<HomeContract.View>
        implements HomeContract.Presenter {

    private static final String TAG = LogUtils.getLogTag(HomePresenter.class);

    private EventChannel.Subscription downloadStartedSubscription;

    @Inject
    HomePresenter(AppBus appBus, AppDataStore appDataStore, Activity activity) {
        super(appBus, appDataStore, activity);
    }

    @Override
    public void attachView(HomeContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        if (wasViewRecreated) {

            downloadStartedSubscription =
                    getAppBus().downloadStarted.subscribe(getView()::onDownloadStarted);
        }
    }

    @Override
    public void detachView() {
        super.detachView();

        if (downloadStartedSubscription != null) {
            downloadStartedSubscription.cancel();
        }
    }
}
