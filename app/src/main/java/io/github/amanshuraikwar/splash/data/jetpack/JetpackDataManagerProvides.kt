package io.github.amanshuraikwar.splash.data.jetpack

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import dagger.Module
import dagger.Provides
import io.github.amanshuraikwar.splash.data.jetpack.datasource.CuratedPhotosDataSource
import io.github.amanshuraikwar.splash.di.Qualifiers
import io.github.amanshuraikwar.splash.model.Photo
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

/**
 * Dagger Module to provide Network Data Manager related instances.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 03/05/18.
 */
@Module
class JetpackDataManagerProvides {

    companion object {
        private const val PAGE_SIZE = 30
        private const val PREFETCH_DISTANCE = 10
    }

    @Singleton
    @Provides
    @Qualifiers.CuratedPhotosListing
    fun getCuratedPhotosListing(curatedPhotosDataSource: CuratedPhotosDataSource,
                                pagedListConfig: PagedList.Config,
                                @Qualifiers.NetworkExecutor networkExecutor: Executor)
            : Listing<Photo> {

        val sourceFactory = ContentDataSourceFactory(curatedPhotosDataSource)

        val pagedList = LivePagedListBuilder(sourceFactory, pagedListConfig)
                .setFetchExecutor(networkExecutor)
                .build()

        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }

        return Listing(
                pagedList = pagedList,
                networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                    it.networkState
                },
                retry = {
                    sourceFactory.sourceLiveData.value?.retryAllFailed()
                },
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                },
                refreshState = refreshState
        )
    }

    @Singleton
    @Provides
    @Qualifiers.NetworkExecutor
    fun getNetworkExecutor(): Executor
            = Executors.newFixedThreadPool(5)

    @Singleton
    @Provides
    fun getPagedListConfig()
            = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .setEnablePlaceholders(false)
            .build()!!
}