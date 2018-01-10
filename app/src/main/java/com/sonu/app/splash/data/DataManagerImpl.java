package com.sonu.app.splash.data;

import android.content.Context;

import com.sonu.app.splash.data.cache.NewPhotosCache;
import com.sonu.app.splash.data.cache.PhotosCache;
import com.sonu.app.splash.data.download.DownloadSession;
import com.sonu.app.splash.di.ApplicationContext;
import com.sonu.app.splash.ui.photo.Photo;

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
