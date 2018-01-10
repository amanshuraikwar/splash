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

    private Disposable onHomeNavItemVisibleDsp, downloadStateChangedDsp, quickMessageDsp;

    @Inject
    public HomePresenter(AppBus appBus, DataManager dataManager, Activity activity) {
        super(appBus, dataManager, activity);
    }

    @Override
    public void attachView(HomeContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        Log.d(TAG, "attachView:called");

        // to prevent more than one consumers to one subject
        if (wasViewRecreated) {
            onHomeNavItemVisibleDsp = getAppBus().onHomeNavItemVisible
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            Log.d(TAG, "onHomeNavItemVisible:consumed");
                            getView().setBnvItemSelected(integer);
                        }
                    });

            downloadStateChangedDsp = getAppBus().onDownloadStateChange
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(
                                Integer t)
                                throws Exception {

                            PhotoDownloadService.DownloadState downloadState =
                                    getDataManager().getDownloadSession().getDownloadState();

                            switch (downloadState.state) {
                                case IDLE:
                                    getView().hideBottomSheet();
                                    break;
                                default:
                                    getView().showBottomSheet();
                                    break;
                            }
                        }
                    });

            quickMessageDsp = getAppBus().sendQuickMessage
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            getView().showToast(s);
                        }
                    });
        }
    }

    @Override
    public void detachView() {
        super.detachView();

        Log.d(TAG, "detachView:called");

        onHomeNavItemVisibleDsp.dispose();
        downloadStateChangedDsp.dispose();
    }
}
