package com.sonu.app.splash.ui.content;

import android.app.Activity;
import android.util.Log;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public abstract class ContentPresenter<View extends ContentContract.View>
        extends BasePresenterImpl<View> implements ContentContract.Presenter<View> {

    public ContentPresenter(AppBus appBus,
                            DataManager dataManager,
                            Activity activity) {
        super(appBus, dataManager, activity);
    }

    @Override
    public void attachView(View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        Log.d(getTag(), "attachView:called");
        Log.i(getTag(), "attachView:wasViewRecreated="+ wasViewRecreated);

        if (wasViewRecreated) {

            resetList();
        } else {

            if (getView().isListEmpty()) {
                getView().getAllContent();
            }
        }
    }

    @Override
    public void resetList() {
        getView().setupList(getContentCache());
        getView().getAllContent();
    }

    public abstract String getTag();
    public abstract ContentCache getContentCache();
}
