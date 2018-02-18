package com.sonu.app.splash.ui.photostats;

import android.app.Activity;

import com.sonu.app.splash.di.ActivityScoped;
import com.sonu.app.splash.model.unsplash.PhotoStats;

import dagger.Binds;
import dagger.Module;

/**
 * Created by amanshuraikwar on 13/02/18.
 */

@Module
public abstract class PhotoStatsModule {

    @ActivityScoped
    @Binds
    abstract PhotoStatsContract.Presenter getPresenter(PhotoStatsPresenter presenter);

    @ActivityScoped
    @Binds
    abstract Activity getActivity(PhotoStatsActivity activity);
}
