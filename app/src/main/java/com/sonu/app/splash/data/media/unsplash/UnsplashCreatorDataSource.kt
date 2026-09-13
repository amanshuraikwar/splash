package com.sonu.app.splash.data.media.unsplash

import com.sonu.app.splash.data.media.CreatorDataSource
import com.sonu.app.splash.data.media.CreatorPageRequest
import com.sonu.app.splash.data.media.model.Creator
import javax.inject.Inject

class UnsplashCreatorDataSource @Inject constructor(
    private val api: UnsplashApi,
) : CreatorDataSource {
    override fun supports(request: CreatorPageRequest): Boolean = true

    override suspend fun getPage(request: CreatorPageRequest): List<Creator> = when (request) {
        is CreatorPageRequest.Search -> api.searchCreators(
            request.query,
            request.page,
            request.perPage,
        ).results
    }.map(UnsplashMediaMapper::toDomain)

    override suspend fun getByUsername(username: String): Creator =
        UnsplashMediaMapper.toDomain(api.getCreator(username))
}
