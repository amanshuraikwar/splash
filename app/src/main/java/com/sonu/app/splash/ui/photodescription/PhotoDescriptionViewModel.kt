package com.sonu.app.splash.ui.photodescription

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sonu.app.splash.data.DataManager
import com.sonu.app.splash.data.local.room.favourites.FavPhoto
import com.sonu.app.splash.data.local.room.photodownload.PhotoDownload
import com.sonu.app.splash.model.unsplash.Photo
import com.sonu.app.splash.ui.navigation.SplashRoute
import com.sonu.app.splash.util.NumberUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

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
    private val dataManager: DataManager,
    preview: PhotoDescriptionPreview,
) : ViewModel() {

    var uiState by mutableStateOf(
        PhotoDescriptionUiState(
            photoId = preview.photoId,
            preview = preview,
        ),
    )
        private set

    private val disposables = CompositeDisposable()
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

        disposables.add(
            dataManager.isPhotoFav(photoId)
                .flatMap { isFavorite ->
                    if (isFavorite) {
                        dataManager.getFavPhotoById(photoId)
                            .flatMap { favorite -> dataManager.removeFav(favorite) }
                    } else {
                        dataManager.addFav(FavPhoto(photo, NumberUtils.getCurrentDate()))
                    }
                }
                .flatMap { dataManager.isPhotoFav(photoId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { isFavorite ->
                        uiState = uiState.copy(
                            isFavorite = isFavorite,
                            isChangingFavorite = false,
                        )
                    },
                    { throwable ->
                        uiState = uiState.copy(
                            isChangingFavorite = false,
                            actionMessage = throwable.readableMessage("Unable to update bookmark"),
                        )
                    },
                ),
        )
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

        disposables.add(
            Observable.fromCallable {
                val downloadReference = dataManager.downloadPhoto(photo)
                PhotoDownload.Builder(
                    downloadReference,
                    NumberUtils.getCurrentTimeStamp(),
                )
                    .photo(photo)
                    .build()
            }
                .flatMap { photoDownload -> dataManager.addPhotoDownload(photoDownload) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { success ->
                        uiState = uiState.copy(
                            isDownloading = false,
                            actionMessage = if (success) {
                                "Download started"
                            } else {
                                "Unable to start download"
                            },
                        )
                    },
                    { throwable ->
                        uiState = uiState.copy(
                            isDownloading = false,
                            actionMessage = throwable.readableMessage("Unable to start download"),
                        )
                    },
                ),
        )
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

        disposables.add(
            dataManager.getPhotoDescription(uiState.photoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { photo ->
                        uiState = uiState.copy(
                            photo = photo,
                            isLoading = false,
                            errorMessage = null,
                        )
                        fetchingPhoto = false
                    },
                    { throwable ->
                        uiState = uiState.copy(
                            isLoading = false,
                            errorMessage = throwable.readableMessage("Unable to load photo details"),
                        )
                        fetchingPhoto = false
                    },
                ),
        )
    }

    private fun checkFavorite() {
        disposables.add(
            dataManager.isPhotoFav(uiState.photoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { isFavorite ->
                        uiState = uiState.copy(isFavorite = isFavorite)
                    },
                    {
                        uiState = uiState.copy(isFavorite = false)
                    },
                ),
        )
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    class Factory(
        private val dataManager: DataManager,
        private val preview: PhotoDescriptionPreview,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PhotoDescriptionViewModel::class.java)) {
                return PhotoDescriptionViewModel(dataManager, preview) as T
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
