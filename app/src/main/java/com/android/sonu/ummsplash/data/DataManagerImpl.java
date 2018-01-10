package com.android.sonu.ummsplash.data;

import android.content.Context;

import com.android.sonu.ummsplash.data.cache.NewPhotosCache;
import com.android.sonu.ummsplash.data.cache.PhotosCache;
import com.android.sonu.ummsplash.data.download.DownloadSession;
import com.android.sonu.ummsplash.di.ApplicationContext;
import com.android.sonu.ummsplash.ui.photo.Photo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class DataManagerImpl implements DataManager {

    @Inject
    @ApplicationContext
    Context applicationContext;

    @Inject
    DownloadSession downloadSession;

    @Inject
    NewPhotosCache newPhotosCache;

    @Inject
    public DataManagerImpl(){
    }


    @Override
    public Observable<List<Photo>> getMorePhotos(PhotosCache photosCache) {
        return photosCache.getMorePhotos();
    }

    @Override
    public Observable<List<Photo>> getCachedPhotos(PhotosCache photosCache) {
        return photosCache.getCachedPhotos();
    }

    @Override
    public boolean isCacheEmpty(PhotosCache photosCache) {
        return photosCache.isCacheEmpty();
    }

    @Override
    public NewPhotosCache getNewPhotosCache() {
        return newPhotosCache;
    }

    @Override
    public DownloadSession getDownloadSession() {
        return downloadSession;
    }
}
