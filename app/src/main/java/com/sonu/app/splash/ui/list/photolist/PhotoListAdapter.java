package com.sonu.app.splash.ui.list.photolist;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.sonu.app.splash.R;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.PhotosCache;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.ui.list.RecyclerViewAdapter;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.util.ConnectionUtil;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.UiExceptionUtils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amanshuraikwar on 18/01/18.
 */

public class PhotoListAdapter extends RecyclerViewAdapter {

    private static final String TAG = LogUtils.getLogTag(PhotoListAdapter.class);

    private PhotosCache photosCache;
    private DataManager dataManager;
    private AdapterListener adapterListener;

    public PhotoListAdapter(@NonNull FragmentActivity parentActivity,
                            @NonNull ListItemTypeFactory typeFactory,
                            @NonNull PhotosCache photosCache,
                            @NonNull DataManager dataManager,
                            @Nonnull AdapterListener adapterListener) {
        super(parentActivity, typeFactory);
        this.photosCache = photosCache;
        this.dataManager = dataManager;
        this.adapterListener = adapterListener;
    }

    /**
     * to get all photos according to cache state
     * -> if : cache empty then fetch more photos
     * -> else if : cache is not empty fetch cached photos
     */
    public void getAllPhotos() {

        if (dataManager.isCacheEmpty(photosCache)) {
            getMorePhotos();
        } else {
            // getting already cached photos
            getCachedPhotos();
        }
    }

    /**
     * to fetch cached photos from photos cache
     */
    public void getCachedPhotos() {

        dataManager
                .getCachedPhotos(photosCache)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Photo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Photo> photos) {
                        Log.d(TAG, "getMorePhotos:onNext:called");
                        setListItems(adapterListener.createListItems(photos));
                        notifyDataSetChanged();
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "getMorePhotos:onError:called");
                        Log.e(TAG, "getMorePhotos:onError:error="+e);
                        e.printStackTrace();
                        UiExceptionUtils.handleUiException(e, adapterListener);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "getMorePhotos:onCompleted:called");
                    }
                });
    }

    /**
     * to fetch more photos in the photos cache
     */
    public void getMorePhotos() {

        dataManager
                .getMorePhotos(photosCache)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Photo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        adapterListener.showLoading();
                    }

                    @Override
                    public void onNext(List<Photo> photos) {
                        Log.d(TAG, "getMorePhotos:onNext:called");
                        int lastIndex = getItemCount() - 1;
                        addListItems(adapterListener.createListItems(photos));
                        notifyItemRangeInserted(lastIndex+1, photos.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "getMorePhotos:onError:called");
                        Log.e(TAG, "getMorePhotos:onError:error="+e);
                        e.printStackTrace();
                        UiExceptionUtils.handleUiException(e, adapterListener);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "getMorePhotos:onCompleted:called");
                        adapterListener.hideLoading();
                    }
                });
    }

    public static abstract class AdapterListener {

        public abstract ListItem createListItem(Photo photo);

        public List<ListItem> createListItems(List<Photo> photos) {

            List<ListItem> listItems = new ArrayList<>();

            for (Photo photo : photos) {
                listItems.add(createListItem(photo));
            }

            return listItems;
        }

        public abstract void showIoException(int titleStringRes, int messageStringRes);
        public abstract void showUnsplashApiException(int titleStringRes, int messageStringRes);
        public abstract void showUnknownException(String message);
        public abstract void showLoading();
        public abstract void hideLoading();
    }
}
