package com.sonu.app.splash.ui.architecture;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public interface BasePresenter<View extends BaseView> {
    /**
     * Binds presenter with the view when resumed
     * Presenter should perform initializations here
     * @param view view associated with this presenter
     */
    void attachView(View view, boolean wasViewRecreated);

    /**
     * Drops the view when presenter is destroyed
     * Presenter should perform clean-ups here
     */
    void detachView();
}
