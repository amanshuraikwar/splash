package com.sonu.app.splash.ui.collectiondecription;

import android.app.Activity;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.CollectionPhotosCache;
import com.sonu.app.splash.data.download.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 04/02/18.
 */

public class CollectionDescriptionPresenter
        extends BasePresenterImpl<CollectionDescriptionContract.View>
        implements CollectionDescriptionContract.Presenter {

    private CollectionPhotosCache collectionPhotosCache;

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

            if (collectionPhotosCache == null) {
                collectionPhotosCache =
                        getDataManager().getCollectionPhotosCache(getView().getCollectionId());
            }

            getView().setupList(collectionPhotosCache);
            getView().getAllPhotos();
        } else {

            if (getView().isListEmpty()) {
                getView().getAllPhotos();
            }
        }
    }

    @Override
    public void downloadImage(PhotoDownload photoDownload) {
        getDataManager().downloadPhoto(photoDownload);
    }
}
