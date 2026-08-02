package com.sonu.app.splash.ui.photos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sonu.app.splash.data.DataManager
import com.sonu.app.splash.model.unsplash.Photo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

data class PhotosFeedUiState(
    val photos: List<Photo> = emptyList(),
    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = true,
    val errorMessage: String? = null,
)

class PhotosFeedViewModel(
    private val dataManager: DataManager,
) : ViewModel() {

    var uiState by mutableStateOf(PhotosFeedUiState())
        private set

    private val disposables = CompositeDisposable()
    private var hasStarted = false

    fun loadInitial() {
        if (hasStarted) {
            return
        }

        hasStarted = true

        val cache = dataManager.getAllPhotosCache()
        if (cache.isCacheEmpty()) {
            loadMore()
            return
        }

        disposables.add(
            cache.getCachedContent()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { photos ->
                        uiState = uiState.copy(
                            photos = photos,
                            canLoadMore = true,
                            errorMessage = null,
                        )
                    },
                    { throwable ->
                        uiState = uiState.copy(errorMessage = throwable.readableMessage())
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

        val isInitialLoad = currentState.photos.isEmpty()
        uiState = currentState.copy(
            isInitialLoading = isInitialLoad,
            isLoadingMore = !isInitialLoad,
            errorMessage = null,
        )

        disposables.add(
            dataManager.getAllPhotosCache()
                .getMoreContent()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { photos ->
                        uiState = uiState.copy(
                            photos = (uiState.photos + photos).distinctBy { it.id },
                            isInitialLoading = false,
                            isLoadingMore = false,
                            canLoadMore = photos.isNotEmpty(),
                            errorMessage = null,
                        )
                    },
                    { throwable ->
                        uiState = uiState.copy(
                            isInitialLoading = false,
                            isLoadingMore = false,
                            errorMessage = throwable.readableMessage(),
                        )
                    },
                ),
        )
    }

    fun refresh() {
        dataManager.getAllPhotosCache().resetCache()
        uiState = PhotosFeedUiState()
        loadMore()
    }

    fun downloadPhoto(photo: Photo) {
        runCatching {
            dataManager.downloadPhoto(photo)
        }.onFailure { throwable ->
            uiState = uiState.copy(errorMessage = throwable.readableMessage())
        }
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    class Factory(
        private val dataManager: DataManager,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PhotosFeedViewModel::class.java)) {
                return PhotosFeedViewModel(dataManager) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

private fun Throwable.readableMessage(): String {
    return localizedMessage?.takeIf { it.isNotBlank() } ?: "Unable to load photos"
}
