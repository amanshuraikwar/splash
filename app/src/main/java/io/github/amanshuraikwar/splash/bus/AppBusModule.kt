package io.github.amanshuraikwar.splash.bus

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger Module to provide Event Bus related instances.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 07/03/18.
 */
@Module class AppBusModule {

    @Singleton @Provides fun appBus(): AppBus = AppBus()
}