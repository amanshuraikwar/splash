package io.github.amanshuraikwar.splash.data.network

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Dagger Module to provide Network Data Manager related instances.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 30/04/18.
 */
@Module
abstract class NetworkDataManagerModule {

    @Singleton @Binds
    abstract fun getNetworkDataManager(impl: NetworkDataManagerImpl): NetworkDataManager
}