package com.sonu.app.splash.ui.collectiondecription;

import android.app.Activity;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.CollectionPhotosCache;
import com.sonu.app.splash.data.local.room.favourites.FavCollection;
import com.sonu.app.splash.data.local.room.favourites.FavPhoto;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.ui.architecture.PresenterPlugin;
import com.sonu.app.splash.util.NumberUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amanshuraikwar on 04/02/18.
 */

public class CollectionDescriptionPresenter
        extends BasePresenterImpl<CollectionDescriptionContract.View>
        implements CollectionDescriptionContract.Presenter {

    private Disposable favCollectionDisp, bookmarkDisp;

    @Inject
    public CollectionDescriptionPresenter(AppBus appBus,
                                          DataManager dataManager,
                                          Activity activity) {
        super(appBus, dataManager, activity);
    }

    @Override
    public void attachView(CollectionDescriptionContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        if (wasViewRecreated) {

            checkForBookmark();
        }
    }

    private void checkForBookmark() {

        bookmarkDisp = getDataManager()
                .isCollectionFav(Integer.parseInt(getView().getCollectionId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    if (value) {
                        getView().setFavActive();
                    } else {
                        getView().setFavInactive();
                    }
                });
    }

    @Override
    public void onAddToFavClick() {

        favCollectionDisp =
                getDataManager()
                        .isCollectionFav(Integer.parseInt(getView().getCollectionId()))
                        .flatMap(this::getFavObs)
                        .flatMap(temp ->
                                getDataManager().isCollectionFav(
                                        Integer.parseInt(getView().getCollectionId())))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                isFav -> {

                                    if (isFav) {

                                        getView().setFavActive();
                                    } else {

                                        getView().setFavInactive();
                                    }
                                }
                        );
    }

    private Observable<Boolean> getFavObs(Boolean isFav) {

        if (isFav) {

            return getDataManager()
                    .getFavCollectionById(Integer.parseInt(getView().getCollectionId()))
                    .flatMap(getDataManager()::removeFav)
                    .filter(success -> success);
        } else {

            return getDataManager()
                    .addFav(new FavCollection(getView().getCollection(), NumberUtils.getCurrentDate()))
                    .filter(success -> success);
        }
    }

    @Override
    public void detachView() {
        super.detachView();

        if (favCollectionDisp != null) {
            if (!favCollectionDisp.isDisposed()) {
                favCollectionDisp.dispose();
            }
        }

        bookmarkDisp.dispose();
    }
}
