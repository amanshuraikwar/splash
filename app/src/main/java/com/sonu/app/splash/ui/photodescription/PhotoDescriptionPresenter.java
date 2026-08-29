package com.sonu.app.splash.ui.photodescription;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.AppDataStore;
import com.sonu.app.splash.data.local.room.favourites.FavPhoto;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.ui.architecture.PresenterPlugin;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.NumberUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

public class PhotoDescriptionPresenter extends BasePresenterImpl<PhotoDescriptionContract.View>
        implements PhotoDescriptionContract.Presenter {

    private static final String TAG = LogUtils.getLogTag(PhotoDescriptionPresenter.class);

    private boolean fetchingData;

    @Inject
    public PhotoDescriptionPresenter(AppBus appBus, AppDataStore appDataStore, Activity activity) {
        super(appBus, appDataStore, activity);
    }

    @Override
    public void attachView(PhotoDescriptionContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        if (wasViewRecreated) {

            getData();

            checkForBookmark();
        }
    }

    private void checkForBookmark() {

        runInBackground(
                () -> getAppDataStore().isPhotoFav(getView().getCurPhotoId()),
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
    public synchronized void getData() {

        Log.d(TAG, "getData:called");
        Log.i(TAG, "getData:locked="+fetchingData);

        if (!fetchingData) {

            fetchingData = true;

            getView().showLoading();
            runInBackground(
                    () -> getListItems(
                            getAppDataStore().getPhotoDescription(getView().getCurPhotoId())),
                    listItems -> {
                        Log.d(TAG, "getPhotoDescription:completed");
                        getView().displayItems(listItems);
                        getView().hideLoading();
                        fetchingData = false;
                    },
                    throwable -> {
                        Log.e(TAG, "getPhotoDescription:error=" + throwable);
                        getView().showError();
                        fetchingData = false;
                    });
        }
    }

    private List<ListItem> getListItems(Photo photo) {

        List<ListItem> listItems = new ArrayList<>();
        listItems.add(
                new PhotoDescriptionUiElements.FourThreeEmptyListItem(
                        Color.parseColor(getView().getCurPhoto().getColor())));
        listItems.add(getView().getLocationListItem(photo.getLocation()));
        listItems.add(getView().getDescriptionTextListItem(photo.getDescription()));
        listItems.add(getView().getPhotoUserListItem(photo.getUser()));
        listItems.add(getView().getPhotoInfoListItem(photo.getId(), photo.getExif()));
        return listItems;
    }

    @Override
    public void downloadPhoto(Photo photo) {
        PresenterPlugin.DownloadPhoto.downloadPhoto(photo, this);
    }

    @Override
    public void onAddToFavClick() {

        runInBackground(
                () -> {
                    boolean isFavorite = getAppDataStore().isPhotoFav(getView().getCurPhotoId());
                    if (isFavorite) {
                        FavPhoto favorite = getAppDataStore()
                                .getFavPhotoById(getView().getCurPhotoId());
                        if (favorite != null) {
                            getAppDataStore().removeFav(favorite);
                        }
                    } else {
                        getAppDataStore().addFav(
                                new FavPhoto(getView().getCurPhoto(), NumberUtils.getCurrentDate()));
                    }
                    return getAppDataStore().isPhotoFav(getView().getCurPhotoId());
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
