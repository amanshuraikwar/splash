package com.sonu.app.splash.ui.photos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sonu.app.splash.data.media.MediaContentCache
import com.sonu.app.splash.data.media.MediaPageRequest
import com.sonu.app.splash.data.media.MediaPageRequestFactory
import com.sonu.app.splash.data.media.MediaRepository
import com.sonu.app.splash.model.unsplash.Photo
import com.sonu.app.splash.ui.legacy.LegacyUiModelMapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

data class PhotosFeedUiState(
    val photos: List<Photo> = emptyList(),
    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = true,
    val errorMessage: String? = null,
)

internal class PhotosFeedViewModel(
    mediaRepository: MediaRepository,
) : ViewModel() {

    private val contentCache = MediaContentCache(
        repository = mediaRepository,
        requestFactory = MediaPageRequestFactory { page -> MediaPageRequest.All(page) },
    )

    var uiState by mutableStateOf(PhotosFeedUiState())
        private set

    private var hasStarted = false

    init {
        viewModelScope.launch {
            contentCache.state.collect { state ->
                uiState = PhotosFeedUiState(
                    photos = state.items.map(LegacyUiModelMapper::toPhoto),
                    isInitialLoading = state.isLoading && state.items.isEmpty(),
                    isLoadingMore = state.isLoading && state.items.isNotEmpty(),
                    canLoadMore = state.canLoadMore,
                    errorMessage = state.error?.readableMessage(),
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
        private val mediaRepository: MediaRepository,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PhotosFeedViewModel::class.java)) {
                return PhotosFeedViewModel(mediaRepository) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

private fun Throwable.readableMessage(): String {
    return localizedMessage?.takeIf { it.isNotBlank() } ?: "Unable to load media"
}
