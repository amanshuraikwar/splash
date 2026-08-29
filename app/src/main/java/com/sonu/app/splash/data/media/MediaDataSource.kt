package com.sonu.app.splash.data.media

import com.sonu.app.splash.data.media.model.Creator
import com.sonu.app.splash.data.media.model.Media
import com.sonu.app.splash.data.media.model.MediaStats

interface MediaDataSource {
    fun supports(request: MediaPageRequest): Boolean

    suspend fun getPage(request: MediaPageRequest): List<Media>

    suspend fun getById(id: String): Media

    suspend fun getStatistics(id: String): MediaStats

    suspend fun getCreator(username: String): Creator
}
