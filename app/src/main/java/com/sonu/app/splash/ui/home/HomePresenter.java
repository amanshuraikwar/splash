package com.sonu.app.splash.ui.home;

import android.app.Activity;
import android.util.Log;

import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.download.PhotoDownloadService;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class HomePresenter
        extends BasePresenterImpl<HomeContract.View>
        implements HomeContract.Presenter {

    private static final String TAG = LogUtils.getLogTag(HomePresenter.class);

    @Inject
    HomePresenter(AppBus appBus, DataManager dataManager, Activity activity) {
        super(appBus, dataManager, activity);
    }
}
