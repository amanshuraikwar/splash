package com.sonu.app.splash.ui.allphotos;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.sonu.app.splash.R;
import com.sonu.app.splash.data.network.unsplashapi.UnsplashApiException;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.NewPhotosCache;
import com.sonu.app.splash.data.download.PhotoDownload;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.ui.photo.PhotoListItem;
import com.sonu.app.splash.ui.photo.PhotoOnClickListener;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.util.ConnectionUtil;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.PermissionsHelper;
import com.sonu.app.splash.util.UiExceptionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

public class AllPhotosPresenter
        extends BasePresenterImpl<AllPhotosContract.View>
        implements AllPhotosContract.Presenter {

    private static final String TAG = LogUtils.getLogTag(AllPhotosPresenter.class);

    private NewPhotosCache newPhotosCache;

    @Inject
    public AllPhotosPresenter(AppBus appBus,
                              DataManager dataManager,
                              Activity activity) {
        super(appBus, dataManager, activity);

        this.newPhotosCache = dataManager.getNewPhotosCache();
    }

    @Override
    public void attachView(AllPhotosContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        Log.d(TAG, "attachView:called");
        Log.i(TAG, "attachView:wasViewRecreated="+ wasViewRecreated);

        if (wasViewRecreated) {

            getView().setupList(getDataManager(), newPhotosCache);
            getView().getAllPhotos();
        } else {

            if (getView().isListEmpty()) {
                getView().getAllPhotos();
            }
        }
    }

    @Override
    public void onDownloadBtnClick(Photo photo) {

        Log.d(TAG, "onDownloadBtnClick:called");

        if (PermissionsHelper.checkStoragePermission(getActivity())) {
            getView().downloadPhoto(new PhotoDownload(photo));
        }

        // todo complete this part
                    /*
                    Intent intent = new Intent(getActivity(), PhotoDownloadService.class);

                    // setting action
                    intent
                            .putExtra(
                                    PhotoDownloadService.KEY_ACTION,
                                    PhotoDownloadService.ACTION_ADD_TO_DOWNLOAD_QUEUE);

                    // converting Photo --> PhotoDownload and sending to PhotoDownloadService
                    intent
                            .putExtra(
                                    PhotoDownloadService.KEY_PHOTO_DOWNLOAD,
                                    new PhotoDownload(photo));

                    getActivity().startService(intent);
                    */
    }

    @Override
    public void onPhotoClick(Photo photo) {

        Log.d(TAG, "onPhotoClick:called");

        getView().startPhotoDescriptionActivity(photo);
    }
}
