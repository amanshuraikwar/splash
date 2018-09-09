package io.github.amanshuraikwar.splash.ui.main

import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import io.github.amanshuraikwar.splash.di.ActivityContext
import io.github.amanshuraikwar.splash.di.ActivityScope
import io.github.amanshuraikwar.splash.ui.home.HomeContract
import io.github.amanshuraikwar.splash.ui.home.HomePresenter

/**
 * Dagger Module to provide MainActivity related instances.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 30/04/18.
 */
@Module abstract class HomeModule {

    @ActivityScope @Binds
    abstract fun prsntr(presenter: HomePresenter): HomeContract.Presenter

    @ActivityScope @Binds @ActivityContext
    abstract fun activity(activity: AppCompatActivity): AppCompatActivity
}