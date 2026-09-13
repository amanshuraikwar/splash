package com.sonu.app.splash.data.media

sealed interface CollectionPageRequest {
    val page: Int
    val perPage: Int

    data class All(
        override val page: Int,
        override val perPage: Int = DEFAULT_PER_PAGE,
    ) : CollectionPageRequest

    data class Featured(
        override val page: Int,
        override val perPage: Int = DEFAULT_PER_PAGE,
    ) : CollectionPageRequest

    data class Creator(
        val username: String,
        override val page: Int,
        override val perPage: Int = DEFAULT_PER_PAGE,
    ) : CollectionPageRequest

    data class Search(
        val query: String,
        override val page: Int,
        override val perPage: Int = DEFAULT_PER_PAGE,
    ) : CollectionPageRequest
}

private const val DEFAULT_PER_PAGE = 30
