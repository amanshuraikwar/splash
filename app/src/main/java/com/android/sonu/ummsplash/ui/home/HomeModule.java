package com.android.sonu.ummsplash.ui.home;

import android.app.Activity;

import com.android.sonu.ummsplash.di.ActivityScoped;
import com.android.sonu.ummsplash.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

@Module
public abstract class HomeModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HomeFragment homeFragment();

    @ActivityScoped
    @Binds
    abstract HomeContract.Presenter getHomePresenter(HomePresenter presenter);

    @ActivityScoped
    @Binds
    abstract Activity activity(HomeActivity activity);
}
