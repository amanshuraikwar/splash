package com.sonu.app.splash.data.cache;

import com.sonu.app.splash.data.media.CollectionPageLoader;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.util.LogUtils;

import java.util.List;
import java.util.stream.Collectors;

import com.sonu.app.splash.ui.legacy.LegacyUiModelMapper;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 17/01/18.
 */

public class UserCollectionsCache extends SimpleContentCache<Collection> {

    private static final String TAG = LogUtils.getLogTag(UserCollectionsCache.class);

    private String username;

    @Inject
    public UserCollectionsCache(CollectionPageLoader pageLoader,
                                String username) {
        super();
        this.pageLoader = pageLoader;
        this.username = username;
    }

    private final CollectionPageLoader pageLoader;

    @Override
    protected List<Collection> fetchPage(int page) {
        return pageLoader.loadCreator(username, page).stream()
                .map(LegacyUiModelMapper::toCollection)
                .collect(Collectors.toList());
    }

    @Override
    protected String getTag() {
        return TAG;
    }

}
