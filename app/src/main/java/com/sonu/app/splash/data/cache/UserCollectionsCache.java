package com.sonu.app.splash.data.cache;

import com.google.gson.JsonElement;
import com.sonu.app.splash.data.network.unsplashapi.ApiEndpoints;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.UnsplashJsonUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 17/01/18.
 */

public class UserCollectionsCache extends SimpleContentCache<Collection> {

    private static final String TAG = LogUtils.getLogTag(UserCollectionsCache.class);

    private String username;

    @Inject
    public UserCollectionsCache(RequestHandler requestHandler,
                                String username) {
        super(requestHandler);
        this.username = username;
    }

    @Override
    protected String getApiEndpoint() {
        return String.format(ApiEndpoints.GET_USER_COLLECTIONS, username, "%s");
    }

    @Override
    protected String getTag() {
        return TAG;
    }

    @Override
    Collection getDataModelFromJson(JsonElement element) {
        return UnsplashJsonUtils.buildCollectionObj(element.getAsJsonObject());
    }
}
