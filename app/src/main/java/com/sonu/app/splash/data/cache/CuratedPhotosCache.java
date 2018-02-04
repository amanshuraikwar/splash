package com.sonu.app.splash.data.cache;

import com.sonu.app.splash.data.network.unsplashapi.ApiEndpoints;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class CuratedPhotosCache extends PhotosCache {

    private static final String TAG = LogUtils.getLogTag(CuratedPhotosCache.class);

    @Inject
    public CuratedPhotosCache(RequestHandler requestHandler) {
        super(requestHandler);
    }

    @Override
    String getApiEndpoint() {
        return String.format(ApiEndpoints.GET_CURATED_PHOTOS, "%s", getOrdering());
    }

    @Override
    String getTag() {
        return TAG;
    }
}
