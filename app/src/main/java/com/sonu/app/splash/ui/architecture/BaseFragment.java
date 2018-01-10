package com.sonu.app.splash.ui.architecture;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class BaseFragment<Presenter extends BasePresenter>
        extends DaggerFragment implements BaseView {

    // to change the variable name independently
    @Inject
    Presenter presenter;

    // to avoid multiple initialisations of single time things
    boolean wasViewRecreated = true;

    /**
     * provides the presenter to sub classes
     * @return presenter for current view
     */
    protected Presenter getPresenter() {
        return presenter;
    }

    /**
     * provides a way for parent view to provide custom onBackPressed()
     * @return if false then default onBackPressed() is called in the parent
     */
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

        // attaching current view to the presenter
        presenter.attachView(this, wasViewRecreated);
        wasViewRecreated = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        wasViewRecreated = true;

        // detaching view to the presenter
        presenter.detachView();
    }
}
