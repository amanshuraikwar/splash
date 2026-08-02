package com.sonu.app.splash.ui.downloads;

import com.sonu.app.splash.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

/**
 * Created by amanshuraikwar on 24/12/17.
 */

@Module
public abstract class DownloadsModule {

    @ActivityScoped
    @Binds
    abstract DownloadsContract.Presenter getDownloadsPresenter(DownloadsPresenter presenter);
}
