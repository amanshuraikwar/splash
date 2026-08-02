package com.sonu.app.splash.ui.search;

import com.sonu.app.splash.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

/**
 * Created by amanshuraikwar on 02/02/18.
 */

@Module
public abstract class SearchModule {

    @ActivityScoped
    @Binds
    abstract SearchContract.Presenter getSearchPresenter(SearchPresenter presenter);
}
