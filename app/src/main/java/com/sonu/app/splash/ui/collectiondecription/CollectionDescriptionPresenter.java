package com.sonu.app.splash.ui.collectiondecription;

import android.app.Activity;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.AppDataStore;
import com.sonu.app.splash.data.local.room.favourites.FavCollection;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.ui.architecture.PresenterPlugin;
import com.sonu.app.splash.util.NumberUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 04/02/18.
 */

public class CollectionDescriptionPresenter
        extends BasePresenterImpl<CollectionDescriptionContract.View>
        implements CollectionDescriptionContract.Presenter {

    @Inject
    public CollectionDescriptionPresenter(AppBus appBus,
                                          AppDataStore appDataStore,
                                          Activity activity) {
        super(appBus, appDataStore, activity);
    }

    @Override
    public void attachView(CollectionDescriptionContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        if (wasViewRecreated) {

            checkForBookmark();
        }
    }

    private void checkForBookmark() {

        runInBackground(
                () -> getAppDataStore().isCollectionFav(getView().getCollectionId()),
                value -> {
                    if (value) {
                        getView().setFavActive();
                    } else {
                        getView().setFavInactive();
                    }
                },
                throwable -> getView().setFavInactive());
    }

    @Override
    public void onAddToFavClick() {

        runInBackground(
                () -> {
                    boolean isFavorite = getAppDataStore()
                            .isCollectionFav(getView().getCollectionId());
                    if (isFavorite) {
                        FavCollection favorite = getAppDataStore()
                                .getFavCollectionById(getView().getCollectionId());
                        if (favorite != null) {
                            getAppDataStore().removeFav(favorite);
                        }
                    } else {
                        getAppDataStore().addFav(
                                new FavCollection(
                                        getView().getCollection(),
                                        NumberUtils.getCurrentDate()));
                    }
                    return getAppDataStore().isCollectionFav(getView().getCollectionId());
                },
                isFavorite -> {
                    if (isFavorite) {
                        getView().setFavActive();
                    } else {
                        getView().setFavInactive();
                    }
                },
                throwable -> getView().setFavInactive());
    }

    @Override
    public void detachView() {
        super.detachView();

    }
}
