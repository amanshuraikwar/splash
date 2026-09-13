package com.sonu.app.splash.data.media

import com.sonu.app.splash.data.media.model.Creator

interface CreatorDataSource {
    fun supports(request: CreatorPageRequest): Boolean

    suspend fun getPage(request: CreatorPageRequest): List<Creator>

    suspend fun getByUsername(username: String): Creator
}
