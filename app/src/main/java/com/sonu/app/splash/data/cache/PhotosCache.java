package com.sonu.app.splash.data.cache;

import com.google.gson.JsonElement;
import com.sonu.app.splash.data.network.unsplashapi.ApiEndpoints;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.UnsplashJsonUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public abstract class PhotosCache extends SimpleContentCache<Photo> {

    private static final String TAG = LogUtils.getLogTag(PhotosCache.class);

    private enum ORDER_BY {LATEST, OLDEST, POPULAR}

    @SuppressWarnings("FieldCanBeLocal")
    private final String ORDERING_LATEST = "latest";

    @SuppressWarnings("FieldCanBeLocal")
    private final String ORDERING_OLDEST = "oldest";

    @SuppressWarnings("FieldCanBeLocal")
    private final String ORDERING_POPULAR = "popular";

    private ORDER_BY ordering;

    PhotosCache(RequestHandler requestHandler) {
        super(requestHandler);

        // default ordering
        ordering = ORDER_BY.LATEST;
    }

    public boolean setOrdering(ORDER_BY ordering) {
        if (ordering != this.ordering) {
            this.ordering = ordering;
            resetCache();
            return true;
        }

        return false;
    }

    public String getOrdering() {
        switch (ordering) {
            case LATEST:
                return ORDERING_LATEST;
            case OLDEST:
                return ORDERING_OLDEST;
            case POPULAR:
                return ORDERING_POPULAR;
        }

        return ORDERING_LATEST;
    }

    @Override
    Photo getDataModelFromJson(JsonElement element) {
        return UnsplashJsonUtils.getPhotoObj(element);
    }
}
