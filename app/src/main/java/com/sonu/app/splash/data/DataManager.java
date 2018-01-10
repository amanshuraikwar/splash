package com.sonu.app.splash.data;

import com.sonu.app.splash.data.cache.NewPhotosCache;
import com.sonu.app.splash.data.cache.PhotosCache;
import com.sonu.app.splash.data.download.DownloadSession;
import com.sonu.app.splash.data.local.LocalDataManager;
import com.sonu.app.splash.data.network.NetworkDataManager;
import com.sonu.app.splash.ui.photo.Photo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public interface DataManager extends LocalDataManager, NetworkDataManager{

    Observable<List<Photo>> getMorePhotos(PhotosCache photosCache);
    Observable<List<Photo>> getCachedPhotos(PhotosCache photosCache);
    boolean isCacheEmpty(PhotosCache photosCache);

    NewPhotosCache getNewPhotosCache();

    DownloadSession getDownloadSession();
}
