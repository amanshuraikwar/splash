package com.sonu.app.splash.ui.collectiondecription;

import android.app.Activity;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.CollectionPhotosCache;
import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.ui.architecture.PresenterPlugin;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by amanshuraikwar on 04/02/18.
 */

public class CollectionDescriptionPresenter
        extends BasePresenterImpl<CollectionDescriptionContract.View>
        implements CollectionDescriptionContract.Presenter {

    private CollectionPhotosCache collectionPhotosCache;

    private Disposable downloadPhotoDisp;

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
    public void downloadImage(Photo photo) {
        downloadPhotoDisp = PresenterPlugin.DownloadPhoto.downloadPhoto(photo, this);
    }

    @Override
    public void detachView() {
        super.detachView();

        if (downloadPhotoDisp != null) {
            if (!downloadPhotoDisp.isDisposed()) {
                downloadPhotoDisp.dispose();
            }
        }
    }
}
