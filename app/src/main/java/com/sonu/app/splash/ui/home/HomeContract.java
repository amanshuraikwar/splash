package com.sonu.app.splash.ui.home;

import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public interface HomeContract {

    interface View extends BaseView {
        void setBnvItemSelected(int homeNavItemId);
        void hideBottomSheet();
        void showBottomSheet();
        void showToast(String message);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
