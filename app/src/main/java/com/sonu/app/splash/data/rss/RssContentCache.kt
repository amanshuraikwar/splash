package com.sonu.app.splash.data.rss

import com.sonu.app.splash.data.rss.model.RssItem
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class RssContentState(
    val items: List<RssItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)

class RssContentCache @Inject constructor(
    private val repository: RssRepository,
) {
    private val _state = MutableStateFlow(RssContentState())
    val state: StateFlow<RssContentState> = _state.asStateFlow()

    suspend fun refresh() {
        if (_state.value.isLoading) {
            return
        }

        _state.update { it.copy(isLoading = true, error = null) }
        runCatching { repository.getItems() }
            .onSuccess { items ->
                _state.value = RssContentState(items = items)
            }
            .onFailure { error ->
                _state.update { it.copy(isLoading = false, error = error) }
            }
    }
}
