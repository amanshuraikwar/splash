package com.sonu.app.splash.data.cache;

import com.google.gson.JsonElement;
import com.sonu.app.splash.data.network.unsplashapi.ApiEndpoints;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.UnsplashJsonUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class AllCollectionsCache extends SimpleContentCache<Collection> {

    private static final String TAG = LogUtils.getLogTag(AllCollectionsCache.class);

    @Inject
    public AllCollectionsCache(RequestHandler requestHandler) {
        super(requestHandler);
    }

    @Override
    String getApiEndpoint() {
        return ApiEndpoints.GET_ALL_COLLECTIONS;
    }

    @Override
    String getTag() {
        return TAG;
    }

    @Override
    Collection getDataModelFromJson(JsonElement element) {
        return UnsplashJsonUtils.buildCollectionObj(element.getAsJsonObject());
    }
}
