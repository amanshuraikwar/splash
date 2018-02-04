package com.sonu.app.splash.data.cache;

import com.sonu.app.splash.data.network.unsplashapi.ApiEndpoints;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 17/01/18.
 */

public class CollectionPhotosCache extends PhotosCache {

    private static final String TAG = LogUtils.getLogTag(CollectionPhotosCache.class);

    private String id;

    @Inject
    public CollectionPhotosCache(RequestHandler requestHandler,
                                 String id) {
        super(requestHandler);
        this.id = id;
    }

    @Override
    protected String getApiEndpoint() {
        return String.format(ApiEndpoints.GET_COLLECTION_PHOTOS, id, "%s");
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
