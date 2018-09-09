package io.github.amanshuraikwar.splash.data.jetpack

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import io.github.amanshuraikwar.splash.data.jetpack.datasource.ContentDataSource
import io.github.amanshuraikwar.splash.data.jetpack.datasource.CuratedPhotosDataSource
import io.github.amanshuraikwar.splash.di.Qualifiers
import io.github.amanshuraikwar.splash.model.Photo
import io.github.amanshuraikwar.splash.ui.list.ListItem
import java.util.concurrent.Executor
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class JetpackDataManagerImpl @Inject constructor(
        private val curatedPhotosDataSource: CuratedPhotosDataSource,
        private val pagedListConfig: PagedList.Config,
        @Qualifiers.NetworkExecutor private val networkExecutor: Executor) : JetpackDataManager {

    override fun getCuratedPhotosListing(mapper: (Photo) -> ListItem<*>): Listing<ListItem<*>> {

        val sourceFactory = ContentDataSourceFactory(curatedPhotosDataSource.map(mapper))

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
}