package com.sonu.app.splash.ui.content;

import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

public interface ContentContract {

    interface View extends BaseView {

        boolean isListEmpty();
        void showLoading();
        void hideLoading();
        void setupList(ContentCache contentCache);
        void getAllContent();
    }

    // cannot set the template variable name to 'View' because it gives compilation error >_>
    interface Presenter<StupidView extends ContentContract.View> extends BasePresenter<StupidView> {
        void resetList();
    }
}
