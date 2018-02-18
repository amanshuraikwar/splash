package com.sonu.app.splash.ui.content;

import com.sonu.app.splash.di.ActivityScoped;
import com.sonu.app.splash.di.FragmentScoped;
import com.sonu.app.splash.ui.content.allcollections.AllCollectionsContract;
import com.sonu.app.splash.ui.content.allcollections.AllCollectionsFragment;
import com.sonu.app.splash.ui.content.allphotos.AllPhotosContract;
import com.sonu.app.splash.ui.content.allphotos.AllPhotosFragment;
import com.sonu.app.splash.ui.content.collectionphotos.CollectionPhotosContract;
import com.sonu.app.splash.ui.content.collectionphotos.CollectionPhotosFragment;
import com.sonu.app.splash.ui.content.curatedphotos.CuratedPhotosContract;
import com.sonu.app.splash.ui.content.curatedphotos.CuratedPhotosFragment;
import com.sonu.app.splash.ui.content.featuredcollections.FeaturedCollectionsContract;
import com.sonu.app.splash.ui.content.featuredcollections.FeaturedCollectionsFragment;
import com.sonu.app.splash.ui.content.searchcollections.SearchCollectionsContract;
import com.sonu.app.splash.ui.content.searchcollections.SearchCollectionsFragment;
import com.sonu.app.splash.ui.content.searchphotos.SearchPhotosContract;
import com.sonu.app.splash.ui.content.searchphotos.SearchPhotosFragment;
import com.sonu.app.splash.ui.content.searchusers.SearchUsersContract;
import com.sonu.app.splash.ui.content.searchusers.SearchUsersFragment;
import com.sonu.app.splash.ui.content.usercollections.UserCollectionsContract;
import com.sonu.app.splash.ui.content.usercollections.UserCollectionsFragment;
import com.sonu.app.splash.ui.content.userphotos.UserPhotosContract;
import com.sonu.app.splash.ui.content.userphotos.UserPhotosFragment;

import butterknife.BindView;
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

    @FragmentScoped
    @ContributesAndroidInjector
    abstract UserPhotosFragment getUserPhotosFragment();

    @ActivityScoped
    @Binds
    abstract UserPhotosContract.Presenter getUserPhotosPresenter(UserPhotosContract.PresenterImpl presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract UserCollectionsFragment getUserCollectionsFragment();

    @ActivityScoped
    @Binds
    abstract UserCollectionsContract.Presenter getUserCollectionsPresenter(UserCollectionsContract.PresenterImpl presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract SearchPhotosFragment getSearchPhotosFragment();

    @ActivityScoped
    @Binds
    abstract SearchPhotosContract.Presenter getSearchPhotosPresenter(SearchPhotosContract.PresenterImpl presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract SearchCollectionsFragment getSearchCollectionsFragment();

    @ActivityScoped
    @Binds
    abstract SearchCollectionsContract.Presenter getSearchCollectionsPresenter(SearchCollectionsContract.PresenterImpl presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract SearchUsersFragment getSearchUsersFragment();

    @ActivityScoped
    @Binds
    abstract SearchUsersContract.Presenter getSearchUsersPresenter(SearchUsersContract.PresenterImpl presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract CollectionPhotosFragment getCollectionPhotosFragment();

    @ActivityScoped
    @Binds
    abstract CollectionPhotosContract.Presenter getCollectionPhotosPresenter(CollectionPhotosContract.PresenterImpl presenter);
}
