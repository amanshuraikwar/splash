package com.sonu.app.splash.ui.architecture;

import android.app.Activity;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class BasePresenterImpl<View extends BaseView> implements BasePresenter<View> {

    private View view;
    private AppBus appBus;
    private DataManager dataManager;
    private Activity activity;

    public BasePresenterImpl(AppBus appBus, DataManager dataManager, Activity activity) {
        this.appBus = appBus;
        this.dataManager = dataManager;
        this.activity = activity;
    }

    protected View getView() {
        return view;
    }

    protected AppBus getAppBus() {
        return appBus;
    }

    protected DataManager getDataManager() {
        return dataManager;
    }

    protected Activity getActivity() {
        return activity;
    }

    @Override
    public void attachView(View view, boolean wasViewRecreated) {
        // attaching view
        this.view = view;
    }

    @Override
    public void detachView() {
        // detaching view
        this.view = null;
    }
}
