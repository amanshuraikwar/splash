package com.android.sonu.ummsplash.ui.allphotos;

import com.android.sonu.ummsplash.di.ActivityScoped;
import com.android.sonu.ummsplash.di.FragmentScoped;

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
