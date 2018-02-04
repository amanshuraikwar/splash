package com.sonu.app.splash.data.cache;

import com.google.gson.JsonElement;
import com.sonu.app.splash.data.network.unsplashapi.ApiEndpoints;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.ui.collection.Collection;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.UnsplashJsonUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class FeaturedCollectionsCache extends SimpleContentCache<Collection> {

    private static final String TAG = LogUtils.getLogTag(FeaturedCollectionsCache.class);

    @Inject
    public FeaturedCollectionsCache(RequestHandler requestHandler) {
        super(requestHandler);
    }

    @Override
    String getApiEndpoint() {
        return ApiEndpoints.GET_FEATURED_COLLECTIONS;
    }

    @Override
    String getTag() {
        return TAG;
    }

    @Override
    Collection getDataModelFromJson(JsonElement element) {
        return UnsplashJsonUtils.getCollectionObj(element);
    }
}
