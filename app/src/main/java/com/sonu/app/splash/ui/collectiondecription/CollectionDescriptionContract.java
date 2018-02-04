package com.sonu.app.splash.ui.collectiondecription;

import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.data.download.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;

/**
 * Created by amanshuraikwar on 04/02/18.
 */

public interface CollectionDescriptionContract {

    interface View extends BaseView {

        String getCollectionId();
        void setupList(ContentCache contentCache);
        void getAllPhotos();
        boolean isListEmpty();
    }

    interface Presenter extends BasePresenter<View> {

        void downloadImage(PhotoDownload photoDownload);
    }
}
