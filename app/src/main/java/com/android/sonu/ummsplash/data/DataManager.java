package com.android.sonu.ummsplash.data;

import com.android.sonu.ummsplash.data.cache.NewPhotosCache;
import com.android.sonu.ummsplash.data.cache.PhotosCache;
import com.android.sonu.ummsplash.data.download.DownloadSession;
import com.android.sonu.ummsplash.data.local.LocalDataManager;
import com.android.sonu.ummsplash.data.network.NetworkDataManager;
import com.android.sonu.ummsplash.ui.photo.Photo;

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
