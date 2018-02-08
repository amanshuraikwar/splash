package com.sonu.app.splash.data.cache;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.sonu.app.splash.data.network.unsplashapi.ApiEndpoints;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.UnsplashJsonUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class SearchUsersCache extends SearchCache<User> {

    @Inject
    public SearchUsersCache(RequestHandler requestHandler) {
        super(requestHandler);
    }

    @Override
    String getApiEndpoint() {
        return String.format(ApiEndpoints.SEARCH_USERS, getQuery(), "%s");
    }

    @Override
    String getTag() {
        return LogUtils.getLogTag(SearchUsersCache.class);
    }

    @Override
    User getDataModelFromJson(JsonElement element) {
        return UnsplashJsonUtils.buildUserObj(element.getAsJsonObject());
    }

    @Override
    protected JsonElement getMeaningFullData(String body) throws JsonParseException {
        return new JsonParser().parse(body).getAsJsonObject().get("results");
    }
}
