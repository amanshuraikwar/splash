package com.sonu.app.splash.ui.architecture;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by amanshuraikwar on 24/01/18.
 */

public abstract class BaseActivity<Presenter extends BasePresenter>
        extends DaggerAppCompatActivity implements BaseView {

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

    @Override
    public void onResume() {
        super.onResume();

        // attaching current view to the presenter
        presenter.attachView(this, wasViewRecreated);
        wasViewRecreated = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        wasViewRecreated = true;

        // detaching view to the presenter
        presenter.detachView();
    }
}
