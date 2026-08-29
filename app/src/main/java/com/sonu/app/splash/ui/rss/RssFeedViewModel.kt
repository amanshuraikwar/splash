package com.sonu.app.splash.ui.rss

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sonu.app.splash.data.rss.RssContentCache
import com.sonu.app.splash.data.rss.RssRepository
import com.sonu.app.splash.data.rss.model.RssItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

data class RssFeedUiState(
    val items: List<RssItem> = emptyList(),
    val isInitialLoading: Boolean = false,
    val errorMessage: String? = null,
)

internal class RssFeedViewModel(
    repository: RssRepository,
) : ViewModel() {
    private val contentCache = RssContentCache(repository)

    var uiState by mutableStateOf(RssFeedUiState())
        private set

    private var hasStarted = false

    init {
        viewModelScope.launch {
            contentCache.state.collect { state ->
                uiState = RssFeedUiState(
                    items = state.items,
                    isInitialLoading = state.isLoading && state.items.isEmpty(),
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
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            contentCache.refresh()
        }
    }

    class Factory(
        private val repository: RssRepository,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RssFeedViewModel::class.java)) {
                return RssFeedViewModel(repository) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

private fun Throwable.readableMessage(): String {
    return localizedMessage?.takeIf { it.isNotBlank() } ?: "Unable to load RSS feeds"
}
