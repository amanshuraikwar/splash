package com.sonu.app.splash.ui.home;

import android.app.Activity;

import com.sonu.app.splash.di.ActivityScoped;
import com.sonu.app.splash.di.FragmentScoped;
import com.sonu.app.splash.ui.collections.CollectionsFragment;
import com.sonu.app.splash.ui.photos.PhotosFragment;
import com.sonu.app.splash.ui.search.SearchFragment;

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

    @FragmentScoped
    @ContributesAndroidInjector
    abstract PhotosFragment photosFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract CollectionsFragment collectionsFragment();

    @ActivityScoped
    @Binds
    abstract HomeContract.Presenter getHomePresenter(HomePresenter presenter);

    @ActivityScoped
    @Binds
    abstract Activity activity(HomeActivity activity);
}
