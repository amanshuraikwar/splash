package com.sonu.app.splash.ui.architecture;

import android.app.Activity;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.AppDataStore;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

import kotlinx.coroutines.Job;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class BasePresenterImpl<View extends BaseView> implements BasePresenter<View> {

    private View view;
    private AppBus appBus;
    private AppDataStore appDataStore;
    private Activity activity;
    private final CoroutineTaskScope taskScope = new CoroutineTaskScope();

    public BasePresenterImpl(AppBus appBus, AppDataStore appDataStore, Activity activity) {
        this.appBus = appBus;
        this.appDataStore = appDataStore;
        this.activity = activity;
    }

    protected View getView() {
        return view;
    }

    protected AppBus getAppBus() {
        return appBus;
    }

    protected AppDataStore getAppDataStore() {
        return appDataStore;
    }

    protected Activity getActivity() {
        return activity;
    }

    protected <T> Job runInBackground(
            Callable<T> task,
            Consumer<T> onSuccess,
            Consumer<Throwable> onError) {
        return taskScope.launch(task, onSuccess, onError);
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
        taskScope.cancelAll();
    }
}
