package com.sonu.app.splash.ui.photos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sonu.app.splash.data.media.CollectionContentCache
import com.sonu.app.splash.data.media.CollectionPageRequest
import com.sonu.app.splash.data.media.CollectionPageRequestFactory
import com.sonu.app.splash.data.media.CollectionRepository
import com.sonu.app.splash.model.unsplash.Collection as UnsplashCollection
import com.sonu.app.splash.ui.legacy.LegacyUiModelMapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val DEFAULT_COLLECTIONS_QUERY = "photos"

data class CollectionsFeedUiState(
    val collections: List<UnsplashCollection> = emptyList(),
    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = true,
    val errorMessage: String? = null,
)

internal class CollectionsFeedViewModel(
    collectionRepository: CollectionRepository,
    private val searchQuery: String = DEFAULT_COLLECTIONS_QUERY,
) : ViewModel() {

    private val contentCache = CollectionContentCache(
        repository = collectionRepository,
        requestFactory = CollectionPageRequestFactory { page ->
            CollectionPageRequest.Search(searchQuery, page)
        },
    )

    var uiState by mutableStateOf(CollectionsFeedUiState())
        private set

    private var hasStarted = false

    init {
        viewModelScope.launch {
            contentCache.state.collect { state ->
                uiState = CollectionsFeedUiState(
                    collections = state.items.map(LegacyUiModelMapper::toCollection),
                    isInitialLoading = state.isLoading && state.items.isEmpty(),
                    isLoadingMore = state.isLoading && state.items.isNotEmpty(),
                    canLoadMore = state.canLoadMore,
                    errorMessage = state.error?.collectionsReadableMessage(),
                )
            }
        }
    }

    fun loadInitial() {
        if (hasStarted) {
            return
        }

        hasStarted = true

        loadMore()
    }

    fun loadMore() {
        viewModelScope.launch {
            contentCache.loadMore()
        }
    }

    fun refresh() {
        viewModelScope.launch {
            contentCache.refresh()
        }
    }

    class Factory(
        private val collectionRepository: CollectionRepository,
        private val searchQuery: String = DEFAULT_COLLECTIONS_QUERY,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectionsFeedViewModel::class.java)) {
                return CollectionsFeedViewModel(collectionRepository, searchQuery) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

private fun Throwable.collectionsReadableMessage(): String {
    return localizedMessage?.takeIf { it.isNotBlank() } ?: "Unable to load collections"
}
