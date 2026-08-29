package com.sonu.app.splash.data.cache;

import java.util.List;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public interface ContentCache<DataModel> {

    List<DataModel> getMoreContent();
    List<DataModel> getCachedContent();
    boolean isCacheEmpty();
    void resetCache();
}
