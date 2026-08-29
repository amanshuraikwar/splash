package com.sonu.app.splash.data.media.unsplash

import com.sonu.app.splash.data.media.CollectionDataSource
import com.sonu.app.splash.data.media.CollectionPageRequest
import com.sonu.app.splash.data.media.model.MediaCollection
import javax.inject.Inject

class UnsplashCollectionDataSource @Inject constructor(
    private val api: UnsplashApi,
) : CollectionDataSource {
    override fun supports(request: CollectionPageRequest): Boolean = true

    override suspend fun getPage(request: CollectionPageRequest): List<MediaCollection> = when (request) {
        is CollectionPageRequest.All -> api.getCollections(request.page, request.perPage)
        is CollectionPageRequest.Featured -> api.getFeaturedCollections(request.page, request.perPage)
        is CollectionPageRequest.Creator -> api.getCreatorCollections(
            request.username,
            request.page,
            request.perPage,
        )
        is CollectionPageRequest.Search -> api.searchCollections(
            request.query,
            request.page,
            request.perPage,
        ).results
    }.map(UnsplashCollectionMapper::toDomain)
}
