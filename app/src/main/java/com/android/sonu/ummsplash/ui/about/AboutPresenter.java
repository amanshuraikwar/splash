package com.android.sonu.ummsplash.ui.about;

import android.app.Activity;

import com.android.sonu.ummsplash.ui.architecture.BasePresenterImpl;
import com.android.sonu.ummsplash.bus.AppBus;
import com.android.sonu.ummsplash.data.DataManager;
import com.android.sonu.ummsplash.util.LogUtils;

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

    @Override
    public void attachView(AboutContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

//        Log.d(TAG, "attachView:called");
//
//        getAppBus().onHomeNavItemVisible.onNext(getView().getHomeNavItemId());
    }
}
