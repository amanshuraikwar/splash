package com.sonu.app.splash.ui.search;

import android.app.Activity;

import com.sonu.app.splash.di.ActivityScoped;
import com.sonu.app.splash.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by amanshuraikwar on 02/02/18.
 */

@Module
public abstract class SearchModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract SearchFragment searchFragment();

    @ActivityScoped
    @Binds
    abstract SearchContract.Presenter getSearchPresenter(SearchPresenter presenter);
}
