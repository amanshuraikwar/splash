package com.sonu.app.splash.data.media

import com.sonu.app.splash.data.media.model.MediaCollection
import javax.inject.Inject
import kotlin.jvm.JvmSuppressWildcards

class CollectionRepository @Inject constructor(
    private val dataSources: Set<@JvmSuppressWildcards CollectionDataSource>,
) {
    suspend fun getPage(request: CollectionPageRequest): List<MediaCollection> =
        sourceFor(request).getPage(request)

    private fun sourceFor(request: CollectionPageRequest): CollectionDataSource =
        dataSources.firstOrNull { it.supports(request) }
            ?: error("No collection data source supports $request")
}
