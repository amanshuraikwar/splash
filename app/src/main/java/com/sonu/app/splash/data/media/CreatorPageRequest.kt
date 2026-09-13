package com.sonu.app.splash.data.media

sealed interface CreatorPageRequest {
    val page: Int
    val perPage: Int

    data class Search(
        val query: String,
        override val page: Int,
        override val perPage: Int = DEFAULT_PER_PAGE,
    ) : CreatorPageRequest
}

private const val DEFAULT_PER_PAGE = 30
