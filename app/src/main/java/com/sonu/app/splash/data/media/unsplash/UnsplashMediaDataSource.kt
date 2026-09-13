package com.sonu.app.splash.data.media.unsplash

import com.sonu.app.splash.data.media.MediaDataSource
import com.sonu.app.splash.data.media.MediaPageRequest
import com.sonu.app.splash.data.media.model.Creator
import com.sonu.app.splash.data.media.model.Media
import com.sonu.app.splash.data.media.model.MediaStats
import javax.inject.Inject

class UnsplashMediaDataSource @Inject constructor(
    private val api: UnsplashApi,
) : MediaDataSource {
    override fun supports(request: MediaPageRequest): Boolean = true

    override suspend fun getPage(request: MediaPageRequest): List<Media> = when (request) {
        is MediaPageRequest.All -> api.getAllMedia(
            page = request.page,
            order = request.order.value,
            perPage = request.perPage,
        )

        is MediaPageRequest.Curated -> api.getCuratedMedia(
            page = request.page,
            order = request.order.value,
            perPage = request.perPage,
        )

        is MediaPageRequest.Search -> api.searchMedia(
            query = request.query,
            page = request.page,
            perPage = request.perPage,
        ).results

        is MediaPageRequest.Creator -> api.getCreatorMedia(
            username = request.username,
            page = request.page,
            order = request.order.value,
            perPage = request.perPage,
        )

        is MediaPageRequest.Collection -> api.getCollectionMedia(
            id = request.id,
            page = request.page,
            perPage = request.perPage,
        )
    }.map(UnsplashMediaMapper::toDomain)

    override suspend fun getById(id: String): Media =
        UnsplashMediaMapper.toDomain(api.getMedia(id))

    override suspend fun getStatistics(id: String): MediaStats =
        UnsplashMediaMapper.toDomain(api.getMediaStatistics(id))

    override suspend fun getCreator(username: String): Creator =
        UnsplashMediaMapper.toDomain(api.getCreator(username))
}
