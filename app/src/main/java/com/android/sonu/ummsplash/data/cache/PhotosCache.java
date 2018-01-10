package com.android.sonu.ummsplash.data.cache;

import com.android.sonu.ummsplash.ui.photo.Photo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public interface PhotosCache {

    Observable<List<Photo>> getMorePhotos();
    Observable<List<Photo>> getCachedPhotos();
    boolean isCacheEmpty();
}
