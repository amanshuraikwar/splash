package com.sonu.app.splash.data.cache;

import com.sonu.app.splash.data.media.CreatorPageLoader;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.util.LogUtils;

import java.util.List;
import java.util.stream.Collectors;

import com.sonu.app.splash.ui.legacy.LegacyUiModelMapper;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class SearchUsersCache extends SearchCache<User> {

    private final CreatorPageLoader pageLoader;

    @Inject
    public SearchUsersCache(CreatorPageLoader pageLoader) {
        super();
        this.pageLoader = pageLoader;
    }

    @Override
    protected List<User> fetchPage(int page) {
        return pageLoader.loadSearch(getQuery(), page).stream()
                .map(LegacyUiModelMapper::toUser)
                .collect(Collectors.toList());
    }

    @Override
    String getTag() {
        return LogUtils.getLogTag(SearchUsersCache.class);
    }
}
