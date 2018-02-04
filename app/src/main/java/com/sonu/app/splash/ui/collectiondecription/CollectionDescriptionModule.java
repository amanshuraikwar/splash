package com.sonu.app.splash.ui.collectiondecription;

import android.app.Activity;

import com.sonu.app.splash.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

/**
 * Created by amanshuraikwar on 04/02/18.
 */

@Module
public abstract class CollectionDescriptionModule {

    @ActivityScoped
    @Binds
    abstract CollectionDescriptionContract.Presenter getCollectionDescriptionPresenter(CollectionDescriptionPresenter presenter);

    @ActivityScoped
    @Binds
    abstract Activity activity(CollectionDescriptionActivity activity);
}
