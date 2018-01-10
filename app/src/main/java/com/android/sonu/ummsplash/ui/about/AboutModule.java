package com.android.sonu.ummsplash.ui.about;

import android.app.Activity;

import com.android.sonu.ummsplash.di.ActivityScoped;
import com.android.sonu.ummsplash.di.FragmentScoped;
import com.android.sonu.ummsplash.ui.home.HomeActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

@Module
public abstract class AboutModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AboutFragment getAboutFragment();

    @ActivityScoped
    @Binds
    abstract AboutContract.Presenter getAboutPresenter(AboutPresenter presenter);

    @ActivityScoped
    @Binds
    abstract Activity activity(AboutActivity activity);
}
