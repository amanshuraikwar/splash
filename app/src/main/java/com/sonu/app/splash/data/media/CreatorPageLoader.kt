package com.sonu.app.splash.data.media

import com.sonu.app.splash.data.media.model.Creator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/** Java-facing boundary for legacy creator search and detail APIs. */
class CreatorPageLoader @Inject constructor(
    private val repository: CreatorRepository,
) {
    fun loadSearch(query: String, page: Int): List<Creator> = runBlocking(Dispatchers.IO) {
        repository.getPage(CreatorPageRequest.Search(query, page))
    }

    fun loadByUsername(username: String): Creator = runBlocking(Dispatchers.IO) {
        repository.getByUsername(username)
    }
}
