package com.sonu.app.splash.data.cache;

import com.sonu.app.splash.data.media.MediaPageLoader;
import com.sonu.app.splash.data.media.model.Media;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.util.LogUtils;

import java.util.List;
import java.util.stream.Collectors;

import com.sonu.app.splash.ui.legacy.LegacyUiModelMapper;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class SearchPhotosCache extends SearchCache<Photo> {

    private static final String TAG = LogUtils.getLogTag(SearchPhotosCache.class);

    private final MediaPageLoader pageLoader;

    @Inject
    public SearchPhotosCache(MediaPageLoader pageLoader) {
        super();
        this.pageLoader = pageLoader;
    }

    @Override
    protected List<Photo> fetchPage(int page) {
        return pageLoader.loadSearch(getQuery(), page).stream()
                .map(LegacyUiModelMapper::toPhoto)
                .collect(Collectors.toList());
    }

    @Override
    String getTag() {
        return TAG;
    }
}
