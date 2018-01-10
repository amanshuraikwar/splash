package com.android.sonu.ummsplash.ui.about;

import com.android.sonu.ummsplash.ui.architecture.BasePresenter;
import com.android.sonu.ummsplash.ui.architecture.BaseView;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

public interface AboutContract {

    interface View extends BaseView {

        int getHomeNavItemId();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
