package com.sonu.app.splash.ui.photos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sonu.app.splash.data.DataManager
import com.sonu.app.splash.data.cache.SearchCollectionsCache
import com.sonu.app.splash.model.unsplash.Collection as UnsplashCollection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

private const val DEFAULT_COLLECTIONS_QUERY = "photos"

data class CollectionsFeedUiState(
    val collections: List<UnsplashCollection> = emptyList(),
    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = true,
    val errorMessage: String? = null,
)

internal class CollectionsFeedViewModel(
    private val dataManager: DataManager,
    private val searchQuery: String = DEFAULT_COLLECTIONS_QUERY,
) : ViewModel() {

    var uiState by mutableStateOf(CollectionsFeedUiState())
        private set

    private val disposables = CompositeDisposable()
    private var hasStarted = false

    fun loadInitial() {
        if (hasStarted) {
            return
        }

        hasStarted = true

        val cache = contentCache()
        if (cache.isCacheEmpty()) {
            loadMore()
            return
        }

        disposables.add(
            cache.getCachedContent()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { collections ->
                        uiState = uiState.copy(
                            collections = collections,
                            canLoadMore = true,
                            errorMessage = null,
                        )
                    },
                    { throwable ->
                        uiState = uiState.copy(errorMessage = throwable.collectionsReadableMessage())
                    },
                ),
        )
    }

    fun loadMore() {
        val currentState = uiState
        if (currentState.isInitialLoading ||
            currentState.isLoadingMore ||
            !currentState.canLoadMore
        ) {
            return
        }

        val isInitialLoad = currentState.collections.isEmpty()
        uiState = currentState.copy(
            isInitialLoading = isInitialLoad,
            isLoadingMore = !isInitialLoad,
            errorMessage = null,
        )

        disposables.add(
            contentCache()
                .getMoreContent()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { collections ->
                        uiState = uiState.copy(
                            collections = (uiState.collections + collections).distinctBy { it.id },
                            isInitialLoading = false,
                            isLoadingMore = false,
                            canLoadMore = collections.isNotEmpty(),
                            errorMessage = null,
                        )
                    },
                    { throwable ->
                        uiState = uiState.copy(
                            isInitialLoading = false,
                            isLoadingMore = false,
                            errorMessage = throwable.collectionsReadableMessage(),
                        )
                    },
                ),
        )
    }

    fun refresh() {
        contentCache().resetCache()
        uiState = CollectionsFeedUiState()
        loadMore()
    }

    private fun contentCache(): SearchCollectionsCache {
        val cache = dataManager.getSearchCollectionsCache()
        if (cache.getQuery() != searchQuery) {
            cache.setQuery(searchQuery)
        }
        return cache
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    class Factory(
        private val dataManager: DataManager,
        private val searchQuery: String = DEFAULT_COLLECTIONS_QUERY,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectionsFeedViewModel::class.java)) {
                return CollectionsFeedViewModel(dataManager, searchQuery) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

private fun Throwable.collectionsReadableMessage(): String {
    return localizedMessage?.takeIf { it.isNotBlank() } ?: "Unable to load collections"
}
