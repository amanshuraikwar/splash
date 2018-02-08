package com.sonu.app.splash.ui.about;

import android.app.Activity;

import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

public class AboutPresenter extends BasePresenterImpl<AboutContract.View>
        implements AboutContract.Presenter {

    private static final String TAG = LogUtils.getLogTag(AboutPresenter.class);

    @Inject
    public AboutPresenter(AppBus appBus, DataManager dataManager, Activity activity) {
        super(appBus, dataManager, activity);
    }
}
