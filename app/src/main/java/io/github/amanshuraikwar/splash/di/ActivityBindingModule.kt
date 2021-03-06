package io.github.amanshuraikwar.splash.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.amanshuraikwar.splash.ui.home.HomeActivity
import io.github.amanshuraikwar.splash.ui.main.HomeModule

/**
 * Created by amanshuraikwar on 07/03/18.
 */

@Module abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [(HomeModule::class)])
    abstract fun home(): HomeActivity
}