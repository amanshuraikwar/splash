package com.sonu.app.splash.ui.photodescription

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sonu.app.splash.data.download.Downloader
import com.sonu.app.splash.data.local.LocalStore
import com.sonu.app.splash.data.local.room.favourites.FavPhoto
import com.sonu.app.splash.data.local.room.photodownload.PhotoDownload
import com.sonu.app.splash.data.media.MediaRepository
import com.sonu.app.splash.model.unsplash.Photo
import com.sonu.app.splash.ui.navigation.SplashRoute
import com.sonu.app.splash.ui.legacy.LegacyUiModelMapper
import com.sonu.app.splash.util.NumberUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal data class PhotoDescriptionPreview(
    val photoId: String,
    val imageUrl: String? = null,
    val width: Int = 0,
    val height: Int = 0,
    val color: String? = null,
    val description: String? = null,
    val userName: String? = null,
    val username: String? = null,
    val userAvatarUrl: String? = null,
)

internal data class PhotoDescriptionUiState(
    val photoId: String,
    val preview: PhotoDescriptionPreview,
    val photo: Photo? = null,
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val isChangingFavorite: Boolean = false,
    val isDownloading: Boolean = false,
    val errorMessage: String? = null,
    val actionMessage: String? = null,
)

internal class PhotoDescriptionViewModel(
    private val mediaRepository: MediaRepository,
    private val localStore: LocalStore,
    private val downloader: Downloader,
    preview: PhotoDescriptionPreview,
) : ViewModel() {

    var uiState by mutableStateOf(
        PhotoDescriptionUiState(
            photoId = preview.photoId,
            preview = preview,
        ),
    )
        private set

    private var hasStarted = false
    private var fetchingPhoto = false

    fun loadInitial() {
        if (hasStarted) {
            return
        }

        hasStarted = true
        loadPhoto()
        checkFavorite()
    }

    fun retry() {
        loadPhoto()
    }

    fun toggleFavorite() {
        val photo = uiState.photo ?: return
        val photoId = photo.id ?: return
        if (uiState.isChangingFavorite) {
            return
        }

        uiState = uiState.copy(
            isChangingFavorite = true,
            actionMessage = null,
        )

        viewModelScope.launch {
            try {
                val isFavorite = withContext(Dispatchers.IO) {
                    if (localStore.isPhotoFav(photoId)) {
                        localStore.getFavPhotoById(photoId)?.let { localStore.removeFav(it) }
                    } else {
                        localStore.addFav(FavPhoto(photo, NumberUtils.getCurrentDate()))
                    }
                    localStore.isPhotoFav(photoId)
                }
                uiState = uiState.copy(
                    isFavorite = isFavorite,
                    isChangingFavorite = false,
                )
            } catch (throwable: Throwable) {
                uiState = uiState.copy(
                    isChangingFavorite = false,
                    actionMessage = throwable.readableMessage("Unable to update bookmark"),
                )
            }
        }
    }

    fun downloadPhoto() {
        val photo = uiState.photo ?: return
        if (uiState.isDownloading) {
            return
        }

        uiState = uiState.copy(
            isDownloading = true,
            actionMessage = null,
        )

        viewModelScope.launch {
            try {
                val success = withContext(Dispatchers.IO) {
                    val downloadReference = downloader.downloadPhoto(photo)
                    val photoDownload = PhotoDownload.Builder(
                        downloadReference,
                        NumberUtils.getCurrentTimeStamp(),
                    )
                        .photo(photo)
                        .build()
                    localStore.addPhotoDownload(photoDownload)
                }
                uiState = uiState.copy(
                    isDownloading = false,
                    actionMessage = if (success) {
                        "Download started"
                    } else {
                        "Unable to start download"
                    },
                )
            } catch (throwable: Throwable) {
                uiState = uiState.copy(
                    isDownloading = false,
                    actionMessage = throwable.readableMessage("Unable to start download"),
                )
            }
        }
    }

    private fun loadPhoto() {
        if (fetchingPhoto) {
            return
        }

        fetchingPhoto = true
        uiState = uiState.copy(
            isLoading = true,
            errorMessage = null,
            actionMessage = null,
        )

        viewModelScope.launch {
            try {
                val photo = withContext(Dispatchers.IO) {
                    LegacyUiModelMapper.toPhoto(mediaRepository.getById(uiState.photoId))
                }
                uiState = uiState.copy(
                    photo = photo,
                    isLoading = false,
                    errorMessage = null,
                )
            } catch (throwable: Throwable) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = throwable.readableMessage("Unable to load photo details"),
                )
            } finally {
                fetchingPhoto = false
            }
        }
    }

    private fun checkFavorite() {
        viewModelScope.launch {
            val isFavorite = runCatching {
                withContext(Dispatchers.IO) { localStore.isPhotoFav(uiState.photoId) }
            }.getOrDefault(false)
            uiState = uiState.copy(isFavorite = isFavorite)
        }
    }

    class Factory(
        private val mediaRepository: MediaRepository,
        private val localStore: LocalStore,
        private val downloader: Downloader,
        private val preview: PhotoDescriptionPreview,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PhotoDescriptionViewModel::class.java)) {
                return PhotoDescriptionViewModel(
                    mediaRepository,
                    localStore,
                    downloader,
                    preview,
                ) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

internal fun SplashRoute.PhotoDescription.toPreview(): PhotoDescriptionPreview {
    return PhotoDescriptionPreview(
        photoId = photoId,
        imageUrl = imageUrl,
        width = width,
        height = height,
        color = color,
        description = description,
        userName = userName,
        username = username,
        userAvatarUrl = userAvatarUrl,
    )
}

private fun Throwable.readableMessage(fallback: String): String {
    return localizedMessage?.takeIf { it.isNotBlank() } ?: fallback
}
