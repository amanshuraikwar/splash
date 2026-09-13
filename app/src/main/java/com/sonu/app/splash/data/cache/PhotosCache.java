package com.sonu.app.splash.data.cache;

import com.sonu.app.splash.data.media.MediaPageLoader;
import com.sonu.app.splash.data.media.model.Media;
import com.sonu.app.splash.ui.legacy.LegacyUiModelMapper;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.util.LogUtils;

import java.util.List;
import java.util.stream.Collectors;

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

    private final MediaPageLoader pageLoader;

    PhotosCache(MediaPageLoader pageLoader) {
        super();
        this.pageLoader = pageLoader;

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
    protected List<Photo> fetchPage(int page) {
        return loadMediaPage(page).stream()
                .map(LegacyUiModelMapper::toPhoto)
                .collect(Collectors.toList());
    }

    protected abstract List<Media> loadMediaPage(int page);

    protected MediaPageLoader getPageLoader() {
        return pageLoader;
    }
}
