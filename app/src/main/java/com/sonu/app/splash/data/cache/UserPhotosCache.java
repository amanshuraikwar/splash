package com.sonu.app.splash.data.cache;

import com.sonu.app.splash.data.network.unsplashapi.ApiEndpoints;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 17/01/18.
 */

public class UserPhotosCache extends SimplePhotosCache {

    private static final String TAG = LogUtils.getLogTag(UserPhotosCache.class);

    private String username;

    @Inject
    public UserPhotosCache(RequestHandler requestHandler, String username) {
        super(requestHandler);
        this.username = username;
    }

    @Override
    protected String getApiEndpoint() {
        return String.format(ApiEndpoints.GET_USER_PHOTOS, username, "%s");
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
