package com.sonu.app.splash.ui.collectiondecription;

import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;

/**
 * Created by amanshuraikwar on 04/02/18.
 */

public interface CollectionDescriptionContract {

    interface View extends BaseView {

        String getCollectionId();
        Collection getCollection();
        void setFavActive();
        void setFavInactive();
    }

    interface Presenter extends BasePresenter<View> {

        void onAddToFavClick();
    }
}
