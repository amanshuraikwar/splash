package com.sonu.app.splash.data.media

import com.sonu.app.splash.data.media.model.Creator
import com.sonu.app.splash.data.media.model.Media
import com.sonu.app.splash.data.media.model.MediaStats
import javax.inject.Inject
import kotlin.jvm.JvmSuppressWildcards

class MediaRepository @Inject constructor(
    private val dataSources: Set<@JvmSuppressWildcards MediaDataSource>,
) {
    suspend fun getPage(request: MediaPageRequest): List<Media> =
        sourceFor(request).getPage(request)

    suspend fun getById(id: String): Media =
        sourceForAny().getById(id)

    suspend fun getStatistics(id: String): MediaStats =
        sourceForAny().getStatistics(id)

    suspend fun getCreator(username: String): Creator =
        sourceForAny().getCreator(username)

    private fun sourceFor(request: MediaPageRequest): MediaDataSource =
        dataSources.firstOrNull { it.supports(request) }
            ?: error("No media data source supports $request")

    private fun sourceForAny(): MediaDataSource =
        dataSources.singleOrNull()
            ?: dataSources.firstOrNull()
            ?: error("No media data sources are configured")
}
