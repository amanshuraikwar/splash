package com.sonu.app.splash.data.media

import com.sonu.app.splash.data.media.model.Creator
import com.sonu.app.splash.data.media.model.Media
import com.sonu.app.splash.data.media.model.MediaStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/** Java-facing boundary for legacy synchronous cache and presenter APIs. */
class MediaPageLoader @Inject constructor(
    private val repository: MediaRepository,
) {
    fun loadAll(page: Int, order: String): List<Media> =
        runBlocking(Dispatchers.IO) {
            repository.getPage(MediaPageRequest.All(page, order.toMediaOrder()))
        }

    fun loadCurated(page: Int, order: String): List<Media> =
        runBlocking(Dispatchers.IO) {
            repository.getPage(MediaPageRequest.Curated(page, order.toMediaOrder()))
        }

    fun loadSearch(query: String, page: Int): List<Media> =
        runBlocking(Dispatchers.IO) {
            repository.getPage(MediaPageRequest.Search(query, page))
        }

    fun loadCreatorMedia(username: String, page: Int, order: String): List<Media> =
        runBlocking(Dispatchers.IO) {
            repository.getPage(MediaPageRequest.Creator(username, page, order.toMediaOrder()))
        }

    fun loadCollectionMedia(id: String, page: Int): List<Media> =
        runBlocking(Dispatchers.IO) {
            repository.getPage(MediaPageRequest.Collection(id, page))
        }

    fun loadById(id: String): Media = runBlocking(Dispatchers.IO) {
        repository.getById(id)
    }

    fun loadStatistics(id: String): MediaStats = runBlocking(Dispatchers.IO) {
        repository.getStatistics(id)
    }

    fun loadCreator(username: String): Creator = runBlocking(Dispatchers.IO) {
        repository.getCreator(username)
    }

    private fun String.toMediaOrder(): MediaOrder = when (lowercase()) {
        "oldest" -> MediaOrder.OLDEST
        "popular" -> MediaOrder.POPULAR
        else -> MediaOrder.LATEST
    }
}
