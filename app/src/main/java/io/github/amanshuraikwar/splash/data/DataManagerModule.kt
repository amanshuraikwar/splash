package io.github.amanshuraikwar.splash.data

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Dagger Module to provide Data Manager related instances.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 07/03/18.
 */
@Module
abstract class DataManagerModule {

    @Singleton
    @Binds
    abstract fun dataManager(dataManager: DataManagerImpl): DataManager
}