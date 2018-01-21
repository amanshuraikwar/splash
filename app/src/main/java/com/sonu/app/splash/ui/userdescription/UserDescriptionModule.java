package com.sonu.app.splash.ui.userdescription;

import android.app.Activity;

import com.sonu.app.splash.di.ActivityScoped;
import com.sonu.app.splash.di.FragmentScoped;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionContract;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionFragment;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by amanshuraikwar on 12/01/18.
 */

@Module
public abstract class UserDescriptionModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract UserDescriptionFragment getUserDescriptionFragment();

    @ActivityScoped
    @Binds
    abstract UserDescriptionContract.Presenter getUserDescriptionPresenter(UserDescriptionPresenter presenter);

    @ActivityScoped
    @Binds
    abstract Activity activity(UserDescriptionActivity activity);
}
