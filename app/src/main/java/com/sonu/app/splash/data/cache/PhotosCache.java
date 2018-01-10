package com.sonu.app.splash.data.cache;

import com.sonu.app.splash.ui.photo.Photo;

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
