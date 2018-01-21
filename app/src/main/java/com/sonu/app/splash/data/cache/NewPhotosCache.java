package com.sonu.app.splash.data.cache;

import android.util.Log;

import com.sonu.app.splash.data.network.unsplashapi.ApiEndpoints;
import com.sonu.app.splash.data.network.unsplashapi.RequestGenerator;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.data.network.unsplashapi.UnsplashApiException;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.util.LogUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sonu.app.splash.util.UnsplashJsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.Request;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public class NewPhotosCache extends SimplePhotosCache {

    private static final String TAG = LogUtils.getLogTag(NewPhotosCache.class);

    @Inject
    public NewPhotosCache(RequestHandler requestHandler) {
        super(requestHandler);
    }

    @Override
    protected String getApiEndpoint() {
        return ApiEndpoints.GET_NEW_PHOTOS;
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
