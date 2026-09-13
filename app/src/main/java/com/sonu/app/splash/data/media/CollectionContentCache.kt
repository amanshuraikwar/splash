package com.sonu.app.splash.data.media

import com.sonu.app.splash.data.media.model.MediaCollection
import kotlinx.coroutines.flow.StateFlow

fun interface CollectionPageRequestFactory {
    fun create(page: Int): CollectionPageRequest
}

class CollectionContentCache(
    repository: CollectionRepository,
    requestFactory: CollectionPageRequestFactory,
) {
    private val delegate = PagedContentCache(
        loadPage = { page -> repository.getPage(requestFactory.create(page)) },
        keyOf = MediaCollection::id,
    )

    val state: StateFlow<PagedContentState<MediaCollection>> = delegate.state

    suspend fun loadMore(): PagedContentState<MediaCollection> = delegate.loadMore()

    suspend fun refresh(): PagedContentState<MediaCollection> = delegate.refresh()
}
