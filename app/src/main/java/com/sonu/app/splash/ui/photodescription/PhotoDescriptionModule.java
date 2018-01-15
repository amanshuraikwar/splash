package com.sonu.app.splash.ui.photodescription;

import android.app.Activity;

import com.sonu.app.splash.di.ActivityScoped;
import com.sonu.app.splash.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by amanshuraikwar on 12/01/18.
 */

@Module
public abstract class PhotoDescriptionModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract PhotoDescriptionFragment getPhotoDescriptionFragment();

    @ActivityScoped
    @Binds
    abstract PhotoDescriptionContract.Presenter getPhotoDescriptionPresenter(PhotoDescriptionPresenter presenter);

    @ActivityScoped
    @Binds
    abstract Activity activity(PhotoDescriptionActivity activity);
}
