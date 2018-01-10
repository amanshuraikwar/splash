package com.android.sonu.ummsplash.di;

import com.android.sonu.ummsplash.ui.about.AboutActivity;
import com.android.sonu.ummsplash.ui.about.AboutModule;
import com.android.sonu.ummsplash.ui.allphotos.AllPhotosModule;
import com.android.sonu.ummsplash.data.download.PhotoDownloadService;
import com.android.sonu.ummsplash.ui.downloadinfo.DownloadInfoModule;
import com.android.sonu.ummsplash.ui.home.HomeActivity;
import com.android.sonu.ummsplash.ui.home.HomeModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module ActivityBindingModule is on,
 * in our case that will be AppComponent. The beautiful part about this setup is that you never need to tell AppComponent that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that AppComponent exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the specified modules and be aware of a scope annotation @ActivityScoped
 * When Dagger.Android annotation processor runs it will create 4 subcomponents for us.
 */
@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {
            HomeModule.class,
            AllPhotosModule.class,
            DownloadInfoModule.class})
    abstract HomeActivity homeActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {
            AboutModule.class})
    abstract AboutActivity aboutActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract PhotoDownloadService photoDownloadService();
}
