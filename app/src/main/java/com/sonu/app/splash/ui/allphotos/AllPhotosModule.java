package com.sonu.app.splash.ui.allphotos;

import com.sonu.app.splash.di.ActivityScoped;
import com.sonu.app.splash.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

@Module
public abstract class AllPhotosModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AllPhotosFragment getAllPhotosFragment();

    @ActivityScoped
    @Binds
    abstract AllPhotosContract.Presenter getAllPhotosPresenter(AllPhotosPresenter presenter);
}
