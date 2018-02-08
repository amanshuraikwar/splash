package com.sonu.app.splash.ui.downloads;

import com.sonu.app.splash.di.ActivityScoped;
import com.sonu.app.splash.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by amanshuraikwar on 24/12/17.
 */

@Module
public abstract class DownloadsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract DownloadsFragment getDownloadsFragment();

    @ActivityScoped
    @Binds
    abstract DownloadsContract.Presenter getDownloadsPresenter(DownloadsPresenter presenter);
}
