package com.sonu.app.splash.data.cache;

import android.support.annotation.NonNull;

import com.google.gson.JsonElement;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;



/**
 * Created by amanshuraikwar on 28/01/18.
 */

public abstract class SearchCache<DataModel> extends SimpleContentCache<DataModel> {

    private String query;

    SearchCache(RequestHandler requestHandler) {
        super(requestHandler);

        query = "";
    }

    public synchronized void setQuery(@NonNull String query) {
        this.query = query;
        resetCache();
    }

    public synchronized String getQuery() {
        return query;
    }
}
