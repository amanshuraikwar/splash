package com.sonu.app.splash.data.media

sealed interface MediaPageRequest {
    val page: Int
    val perPage: Int

    data class All(
        override val page: Int,
        val order: MediaOrder = MediaOrder.LATEST,
        override val perPage: Int = DEFAULT_PER_PAGE,
    ) : MediaPageRequest

    data class Curated(
        override val page: Int,
        val order: MediaOrder = MediaOrder.LATEST,
        override val perPage: Int = DEFAULT_PER_PAGE,
    ) : MediaPageRequest

    data class Search(
        val query: String,
        override val page: Int,
        override val perPage: Int = DEFAULT_PER_PAGE,
    ) : MediaPageRequest

    data class Creator(
        val username: String,
        override val page: Int,
        val order: MediaOrder = MediaOrder.LATEST,
        override val perPage: Int = DEFAULT_PER_PAGE,
    ) : MediaPageRequest

    data class Collection(
        val id: String,
        override val page: Int,
        override val perPage: Int = DEFAULT_PER_PAGE,
    ) : MediaPageRequest
}

enum class MediaOrder(val value: String) {
    LATEST("latest"),
    OLDEST("oldest"),
    POPULAR("popular"),
}

private const val DEFAULT_PER_PAGE = 30
