package com.sonu.app.splash.ui.content;

import com.sonu.app.splash.di.ActivityScoped;
import com.sonu.app.splash.di.FragmentScoped;
import com.sonu.app.splash.ui.content.allcollections.AllCollectionsContract;
import com.sonu.app.splash.ui.content.allcollections.AllCollectionsFragment;
import com.sonu.app.splash.ui.content.allphotos.AllPhotosContract;
import com.sonu.app.splash.ui.content.allphotos.AllPhotosFragment;
import com.sonu.app.splash.ui.content.curatedphotos.CuratedPhotosContract;
import com.sonu.app.splash.ui.content.curatedphotos.CuratedPhotosFragment;
import com.sonu.app.splash.ui.content.featuredcollections.FeaturedCollectionsContract;
import com.sonu.app.splash.ui.content.featuredcollections.FeaturedCollectionsFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by amanshuraikwar on 28/01/18.
 */
@Module
public abstract class ContentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract CuratedPhotosFragment getCuratedPhotosFragment();

    @ActivityScoped
    @Binds
    abstract CuratedPhotosContract.Presenter getCuratedPhotosPresenter(
            CuratedPhotosContract.PresenterImpl presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AllPhotosFragment getAllPhotosFragment();

    @ActivityScoped
    @Binds
    abstract AllPhotosContract.Presenter getAllPhotosPresenter(
            AllPhotosContract.PresenterImpl presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FeaturedCollectionsFragment getFeaturedCollectionsFragment();

    @ActivityScoped
    @Binds
    abstract FeaturedCollectionsContract.Presenter getFeaturedCollectionsPresenter(
            FeaturedCollectionsContract.PresenterImpl presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AllCollectionsFragment getAllCollectionsFragment();

    @ActivityScoped
    @Binds
    abstract AllCollectionsContract.Presenter getAllCollectionsPresenter(
            AllCollectionsContract.PresenterImpl presenter);
}
