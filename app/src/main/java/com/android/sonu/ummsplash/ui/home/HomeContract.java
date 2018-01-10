package com.android.sonu.ummsplash.ui.home;

import com.android.sonu.ummsplash.ui.architecture.BasePresenter;
import com.android.sonu.ummsplash.ui.architecture.BaseView;

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
