package com.sonu.app.splash.ui.search.activity;

import android.app.Activity;

import com.sonu.app.splash.di.ActivityScoped;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionContract;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created by amanshuraikwar on 12/01/18.
 */

@Module
public abstract class SearchModule {

    @ActivityScoped
    @Binds
    abstract SearchContract.Presenter getSearchPresenter(SearchPresenter presenter);

    @ActivityScoped
    @Binds
    abstract Activity activity(SearchActivity activity);
}
