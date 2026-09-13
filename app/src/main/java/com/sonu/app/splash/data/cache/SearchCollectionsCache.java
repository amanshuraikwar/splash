package com.sonu.app.splash.data.cache;

import com.sonu.app.splash.data.media.CollectionPageLoader;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.util.LogUtils;

import java.util.List;
import java.util.stream.Collectors;

import com.sonu.app.splash.ui.legacy.LegacyUiModelMapper;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class SearchCollectionsCache extends SearchCache<Collection> {

    private static final String TAG = LogUtils.getLogTag(SearchCollectionsCache.class);

    private final CollectionPageLoader pageLoader;

    @Inject
    public SearchCollectionsCache(CollectionPageLoader pageLoader) {
        super();
        this.pageLoader = pageLoader;
    }

    @Override
    protected List<Collection> fetchPage(int page) {
        return pageLoader.loadSearch(getQuery(), page).stream()
                .map(LegacyUiModelMapper::toCollection)
                .collect(Collectors.toList());
    }

    @Override
    String getTag() {
        return TAG;
    }

}
