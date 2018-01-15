package com.sonu.app.splash.di;

import com.sonu.app.splash.ui.about.AboutActivity;
import com.sonu.app.splash.ui.about.AboutModule;
import com.sonu.app.splash.ui.allphotos.AllPhotosModule;
import com.sonu.app.splash.data.download.PhotoDownloadService;
import com.sonu.app.splash.ui.downloadinfo.DownloadInfoModule;
import com.sonu.app.splash.ui.home.HomeActivity;
import com.sonu.app.splash.ui.home.HomeModule;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionModule;

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
    @ContributesAndroidInjector(modules = {
            PhotoDescriptionModule.class})
    abstract PhotoDescriptionActivity photoDescriptionActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract PhotoDownloadService photoDownloadService();
}
