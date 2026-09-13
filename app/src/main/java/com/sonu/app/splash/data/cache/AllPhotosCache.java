package com.sonu.app.splash.data.cache;

import com.sonu.app.splash.data.media.MediaPageLoader;
import com.sonu.app.splash.data.media.model.Media;
import com.sonu.app.splash.util.LogUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class AllPhotosCache extends PhotosCache {

    private static final String TAG = LogUtils.getLogTag(AllPhotosCache.class);

    @Inject
    public AllPhotosCache(MediaPageLoader pageLoader) {
        super(pageLoader);
    }

    @Override
    protected List<Media> loadMediaPage(int page) {
        return getPageLoader().loadAll(page, getOrdering());
    }

    @Override
    String getTag() {
        return TAG;
    }
}
