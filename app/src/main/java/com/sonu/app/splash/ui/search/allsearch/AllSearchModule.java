package com.sonu.app.splash.ui.search.allsearch;

import android.app.Activity;

import com.sonu.app.splash.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

/**
 * Created by amanshuraikwar on 05/02/18.
 */

@Module
public abstract class AllSearchModule {

    @ActivityScoped
    @Binds
    abstract Activity activity(AllSearchActivity activity);
}
