package com.sonu.app.splash.ui.search;

import android.app.Activity;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.AppDataStore;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 02/02/18.
 */

public class SearchPresenter
        extends BasePresenterImpl<SearchContract.View>
        implements SearchContract.Presenter {

    @Inject
    public SearchPresenter(AppBus appBus, AppDataStore appDataStore, Activity activity) {
        super(appBus, appDataStore, activity);
    }

    @Override
    public void attachView(SearchContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        if (wasViewRecreated) {

            if (getView().getInitialQuery() != null) {

                onSearchClick(getView().getInitialQuery());
            }
        }
    }

    @Override
    public void onSearchClick(String query) {

        if (getView().isFirstQuery()) {

            getView().initViewPager(query);
        } else {

            getView().setQuery(query);
        }
    }

    @Override
    public void detachView() {
        super.detachView();

    }
}
