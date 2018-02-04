package com.sonu.app.splash.data.cache;

import com.sonu.app.splash.data.network.unsplashapi.ApiEndpoints;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class AllPhotosCache extends PhotosCache {

    private static final String TAG = LogUtils.getLogTag(AllPhotosCache.class);

    @Inject
    public AllPhotosCache(RequestHandler requestHandler) {
        super(requestHandler);
    }

    @Override
    String getApiEndpoint() {
        return String.format(ApiEndpoints.GET_ALL_PHOTOS, "%s", getOrdering());
    }

    @Override
    String getTag() {
        return TAG;
    }
}
