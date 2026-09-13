package com.sonu.app.splash.data.media

import com.sonu.app.splash.data.media.model.Creator
import javax.inject.Inject
import kotlin.jvm.JvmSuppressWildcards

class CreatorRepository @Inject constructor(
    private val dataSources: Set<@JvmSuppressWildcards CreatorDataSource>,
) {
    suspend fun getPage(request: CreatorPageRequest): List<Creator> =
        sourceFor(request).getPage(request)

    suspend fun getByUsername(username: String): Creator =
        sourceFor(CreatorPageRequest.Search(username, page = 1, perPage = 1))
            .getByUsername(username)

    private fun sourceFor(request: CreatorPageRequest): CreatorDataSource =
        dataSources.firstOrNull { it.supports(request) }
            ?: error("No creator data source supports $request")
}
