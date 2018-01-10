package com.android.sonu.ummsplash.ui.downloadinfo;

import com.android.sonu.ummsplash.di.ActivityScoped;
import com.android.sonu.ummsplash.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by amanshuraikwar on 24/12/17.
 */

@Module
public abstract class DownloadInfoModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract DownloadInfoFragment getDownloadInfoFragment();

    @ActivityScoped
    @Binds
    abstract DownloadInfoContract.Presenter getDownloadInfoPresenter(DownloadInfoPresenter presenter);
}
