package com.sonu.app.splash.data.media

import com.sonu.app.splash.data.media.model.MediaCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/** Java-facing boundary for legacy collection cache APIs. */
class CollectionPageLoader @Inject constructor(
    private val repository: CollectionRepository,
) {
    fun loadAll(page: Int): List<MediaCollection> = load(CollectionPageRequest.All(page))

    fun loadFeatured(page: Int): List<MediaCollection> =
        load(CollectionPageRequest.Featured(page))

    fun loadCreator(username: String, page: Int): List<MediaCollection> =
        load(CollectionPageRequest.Creator(username, page))

    fun loadSearch(query: String, page: Int): List<MediaCollection> =
        load(CollectionPageRequest.Search(query, page))

    private fun load(request: CollectionPageRequest): List<MediaCollection> =
        runBlocking(Dispatchers.IO) { repository.getPage(request) }
}
