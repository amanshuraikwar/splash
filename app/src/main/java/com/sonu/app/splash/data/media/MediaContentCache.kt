package com.sonu.app.splash.data.media

import com.sonu.app.splash.data.media.model.Media
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

data class PagedContentState<Item>(
    val items: List<Item> = emptyList(),
    val isLoading: Boolean = false,
    val canLoadMore: Boolean = true,
    val error: Throwable? = null,
)

fun interface MediaPageRequestFactory {
    fun create(page: Int): MediaPageRequest
}

/**
 * Coroutine replacement for the legacy Rx-backed content caches.
 *
 * The cache owns paging state, while the repository owns source selection. This keeps
 * pagination independent from Unsplash and lets another data source satisfy the same
 * request later.
 */
class PagedContentCache<Item>(
    private val loadPage: suspend (Int) -> List<Item>,
    private val keyOf: (Item) -> Any? = { it },
) {

    private val mutex = Mutex()
    private val _state = MutableStateFlow(PagedContentState<Item>())
    private var nextPage = 1

    val state: StateFlow<PagedContentState<Item>> = _state.asStateFlow()

    suspend fun loadMore(): PagedContentState<Item> {
        if (_state.value.isLoading || !_state.value.canLoadMore) {
            return _state.value
        }

        return mutex.withLock {
            if (_state.value.isLoading || !_state.value.canLoadMore) {
                return@withLock _state.value
            }

            loadNextPage(nextPage)
        }
    }

    suspend fun refresh(): PagedContentState<Item> {
        return mutex.withLock {
            nextPage = 1
            _state.value = PagedContentState(isLoading = true)
            loadNextPage(nextPage, replaceItems = true)
        }
    }

    private suspend fun loadNextPage(
        page: Int,
        replaceItems: Boolean = false,
    ): PagedContentState<Item> {
        _state.value = _state.value.copy(
            isLoading = true,
            error = null,
        )

        return try {
            val pageItems = withContext(Dispatchers.IO) {
                loadPage(page)
            }
            val existingItems = if (replaceItems) emptyList() else _state.value.items
            val mergedItems = (existingItems + pageItems).distinctBy(keyOf)

            nextPage = page + 1
            _state.value = PagedContentState(
                items = mergedItems,
                canLoadMore = pageItems.isNotEmpty(),
            )
            _state.value
        } catch (throwable: Throwable) {
            if (throwable is CancellationException) {
                throw throwable
            }

            _state.value = _state.value.copy(
                isLoading = false,
                error = throwable,
            )
            _state.value
        }
    }
}

typealias MediaContentState = PagedContentState<Media>

class MediaContentCache(
    repository: MediaRepository,
    requestFactory: MediaPageRequestFactory,
) {
    private val delegate = PagedContentCache(
        loadPage = { page -> repository.getPage(requestFactory.create(page)) },
        keyOf = Media::id,
    )

    val state: StateFlow<MediaContentState> = delegate.state

    suspend fun loadMore(): MediaContentState = delegate.loadMore()

    suspend fun refresh(): MediaContentState = delegate.refresh()
}
