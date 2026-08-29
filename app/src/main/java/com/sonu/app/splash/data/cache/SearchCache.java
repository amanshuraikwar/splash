package com.sonu.app.splash.data.cache;

import androidx.annotation.NonNull;



/**
 * Created by amanshuraikwar on 28/01/18.
 */

public abstract class SearchCache<DataModel> extends SimpleContentCache<DataModel> {

    private String query;

    SearchCache() {
        super();

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
