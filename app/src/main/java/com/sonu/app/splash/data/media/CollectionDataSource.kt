package com.sonu.app.splash.data.media

import com.sonu.app.splash.data.media.model.MediaCollection

interface CollectionDataSource {
    fun supports(request: CollectionPageRequest): Boolean

    suspend fun getPage(request: CollectionPageRequest): List<MediaCollection>
}
