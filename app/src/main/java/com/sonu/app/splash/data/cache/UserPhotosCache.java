package com.sonu.app.splash.data.cache;

import com.sonu.app.splash.data.media.MediaPageLoader;
import com.sonu.app.splash.data.media.model.Media;
import com.sonu.app.splash.util.LogUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 17/01/18.
 */

public class UserPhotosCache extends PhotosCache {

    private static final String TAG = LogUtils.getLogTag(UserPhotosCache.class);

    private String username;

    @Inject
    public UserPhotosCache(MediaPageLoader pageLoader,
                           String username) {
        super(pageLoader);
        this.username = username;
    }

    @Override
    protected List<Media> loadMediaPage(int page) {
        return getPageLoader().loadCreatorMedia(username, page, getOrdering());
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
