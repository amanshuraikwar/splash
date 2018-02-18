package com.sonu.app.splash.ui.favs;

import com.sonu.app.splash.di.ActivityScoped;
import com.sonu.app.splash.di.FragmentScoped;
import com.sonu.app.splash.ui.downloads.DownloadsContract;
import com.sonu.app.splash.ui.downloads.DownloadsFragment;
import com.sonu.app.splash.ui.downloads.DownloadsPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by amanshuraikwar on 17/02/18.
 */

@Module
public abstract class FavsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FavsFragment getFavsFragment();

    @ActivityScoped
    @Binds
    abstract FavsContract.Presenter getFavsPresenter(FavsPresenter presenter);
}
