package com.sonu.app.splash.data.cache;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public interface ContentCache<DataModel> {

    Observable<List<DataModel>> getMoreContent();
    Observable<List<DataModel>> getCachedContent();
    boolean isCacheEmpty();
    void resetCache();
}
