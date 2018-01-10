package com.android.sonu.ummsplash.ui.allphotos;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.android.sonu.ummsplash.R;
import com.android.sonu.ummsplash.data.network.unsplashapi.UnsplashApiException;
import com.android.sonu.ummsplash.ui.architecture.BasePresenterImpl;
import com.android.sonu.ummsplash.bus.AppBus;
import com.android.sonu.ummsplash.data.DataManager;
import com.android.sonu.ummsplash.data.cache.NewPhotosCache;
import com.android.sonu.ummsplash.data.download.DownloadSession;
import com.android.sonu.ummsplash.data.download.PhotoDownloadService;
import com.android.sonu.ummsplash.data.download.PhotoDownload;
import com.android.sonu.ummsplash.ui.list.ListItem;
import com.android.sonu.ummsplash.ui.photo.Photo;
import com.android.sonu.ummsplash.ui.photo.PhotoListItem;
import com.android.sonu.ummsplash.ui.photo.PhotoOnClickListener;
import com.android.sonu.ummsplash.util.ConnectionUtil;
import com.android.sonu.ummsplash.util.LogUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

public class AllPhotosPresenter
        extends BasePresenterImpl<AllPhotosContract.View>
        implements AllPhotosContract.Presenter {

    private static final String TAG = LogUtils.getLogTag(AllPhotosPresenter.class);

    private NewPhotosCache newPhotosCache;

    private PhotoOnClickListener photoOnClickListener =
            new PhotoOnClickListener() {
                @Override
                public void onDownloadBtnClick(Photo photo) {

                    Log.d(TAG, "onDownloadBtnClick:called");

                    if (getView().checkPermissions()) {
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
            };

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

//        getAppBus().onHomeNavItemVisible.onNext(getView().getHomeNavItemId());

        if (wasViewRecreated) {

            getAllPhotos();
        } else {

            if (getView().isListEmpty()) {

                getAllPhotos();
            } else {
                // do nothing
            }
        }
    }

    @Override
    public void getAllPhotos() {

        if (getDataManager().isCacheEmpty(newPhotosCache)) {

            if (ConnectionUtil.isConnected(getActivity())) {
                getMorePhotos();
            } else {
                getView().showIoException(
                        R.string.io_exception_title,
                        R.string.io_exception_message);
            }

        } else {
            // getting already cached photos
            getCachedPhotos();
        }
    }

    private void getCachedPhotos() {

        getDataManager()
                .getCachedPhotos(newPhotosCache)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Photo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Photo> photos) {
                        Log.d(TAG, "getMorePhotos:onNext:called");
                        getView().displayPhotos(createListItems(photos));
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "getMorePhotos:onError:called");
                        Log.e(TAG, "getMorePhotos:onError:error="+e);
                        e.printStackTrace();
                        handleException(e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "getMorePhotos:onCompleted:called");

                        getView().hideLoading();
                    }
                });
    }

    @Override
    public void getMorePhotos() {

        if (getView().isListEmpty()) {
            getView().showLoading();
        }

        getDataManager()
                .getMorePhotos(newPhotosCache)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Photo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Photo> photos) {
                        Log.d(TAG, "getMorePhotos:onNext:called");
                        getView().addPhotos(createListItems(photos));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "getMorePhotos:onError:called");
                        Log.e(TAG, "getMorePhotos:onError:error="+e);
                        e.printStackTrace();
                        handleException(e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "getMorePhotos:onCompleted:called");
                        getView().hideLoading();
                    }
                });
    }

    private void handleException(Throwable e) {
        if (e instanceof IOException) {

            getView().showIoException(
                    R.string.io_exception_title,
                    R.string.io_exception_message
            );
        } else if (e instanceof UnsplashApiException) {

            if (((UnsplashApiException) e)
                            .getCode() == UnsplashApiException.CODE_API_LIMIT_EXCEEDED) {

                getView().showUnsplashApiException(
                        R.string.unsplash_api_rate_limit_exceed_title,
                        R.string.unsplash_api_rate_limit_exceed_message);
            } else {

                getView().showUnsplashApiException(
                        R.string.unsplash_api_unknown_exception_title,
                        R.string.unsplash_api_unknown_exception_message);
            }
        } else {
            getView().showUnknownException(e.getMessage());
        }
    }

    private List<ListItem> createListItems(List<Photo> photoList) {
        List<ListItem> listItems = new ArrayList<>();

        for (Photo photo : photoList) {
            PhotoListItem listItem = new PhotoListItem(photo);
            listItem.setOnClickListener(photoOnClickListener);
            listItems.add(listItem);
        }

        return listItems;
    }
}
