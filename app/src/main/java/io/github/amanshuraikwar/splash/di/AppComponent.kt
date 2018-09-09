package io.github.amanshuraikwar.splash.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.github.amanshuraikwar.splash.MyApp
import io.github.amanshuraikwar.splash.bus.AppBus
import io.github.amanshuraikwar.splash.bus.AppBusModule
import io.github.amanshuraikwar.splash.data.DataManager
import io.github.amanshuraikwar.splash.data.DataManagerModule
import io.github.amanshuraikwar.splash.data.DataManagerModuleProvides
import io.github.amanshuraikwar.splash.data.jetpack.JetpackDataManagerModule
import io.github.amanshuraikwar.splash.data.jetpack.JetpackDataManagerProvides
import io.github.amanshuraikwar.splash.data.network.NetworkDataManagerModule
import io.github.amanshuraikwar.splash.data.network.NetworkManagerProvides
import javax.inject.Singleton

/**
 * Created by amanshuraikwar on 07/03/18.
 */
@Singleton
@Component(
        modules = [
            (AppModule::class),
            (NetworkDataManagerModule::class),
            (NetworkManagerProvides::class),
            (JetpackDataManagerModule::class),
            (JetpackDataManagerProvides::class),
            (DataManagerModule::class),
            (DataManagerModuleProvides::class),
            (AppBusModule::class),
            (ActivityBindingModule::class),
            (AndroidSupportInjectionModule::class)])
interface AppComponent : AndroidInjector<MyApp> {

    fun dataManager(): DataManager
    fun appBus(): AppBus

    @Component.Builder interface Builder {

        @BindsInstance fun application(app: Application): Builder
        fun build(): AppComponent
    }
}