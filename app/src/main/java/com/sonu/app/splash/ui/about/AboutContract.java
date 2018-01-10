package com.sonu.app.splash.ui.about;

import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;

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
