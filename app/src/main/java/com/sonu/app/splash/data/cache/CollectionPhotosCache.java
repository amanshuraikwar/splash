package com.sonu.app.splash.data.cache;

import com.sonu.app.splash.data.media.MediaPageLoader;
import com.sonu.app.splash.data.media.model.Media;
import com.sonu.app.splash.util.LogUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 17/01/18.
 */

public class CollectionPhotosCache extends PhotosCache {

    private static final String TAG = LogUtils.getLogTag(CollectionPhotosCache.class);

    private String id;

    @Inject
    public CollectionPhotosCache(MediaPageLoader pageLoader,
                                 String id) {
        super(pageLoader);
        this.id = id;
    }

    @Override
    protected List<Media> loadMediaPage(int page) {
        return getPageLoader().loadCollectionMedia(id, page);
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
