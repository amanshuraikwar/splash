package com.sonu.app.splash.data.cache;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.sonu.app.splash.data.network.unsplashapi.ApiEndpoints;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.UnsplashJsonUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class SearchPhotosCache extends SearchCache<Photo> {

    private static final String TAG = LogUtils.getLogTag(SearchPhotosCache.class);

    @Inject
    public SearchPhotosCache(RequestHandler requestHandler) {
        super(requestHandler);
    }

    @Override
    String getApiEndpoint() {
        return String.format(ApiEndpoints.SEARCH_PHOTOS, getQuery(), "%s");
    }

    @Override
    String getTag() {
        return TAG;
    }

    @Override
    Photo getDataModelFromJson(JsonElement element) {
        return UnsplashJsonUtils.buildPhotoObj(element.getAsJsonObject());
    }

    @Override
    protected JsonElement getMeaningFullData(String body) throws JsonParseException {
        return new JsonParser().parse(body).getAsJsonObject().get("results");
    }
}
