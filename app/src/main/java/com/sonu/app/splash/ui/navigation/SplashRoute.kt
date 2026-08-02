package com.sonu.app.splash.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface SplashRoute : NavKey {
    @Serializable
    data object Home : SplashRoute

    @Serializable
    data object About : SplashRoute

    @Serializable
    data class PhotoDescription(val photoId: String) : SplashRoute

    @Serializable
    data class PhotoFullscreen(val photoId: String) : SplashRoute

    @Serializable
    data class PhotoStats(val photoId: String) : SplashRoute

    @Serializable
    data class UserDescription(val username: String) : SplashRoute

    @Serializable
    data class CollectionDescription(val collectionId: String) : SplashRoute

    @Serializable
    data class AllSearch(val query: String) : SplashRoute
}

object SplashSharedElementKey {
    fun photoImage(photoId: String) = "photo:$photoId:image"
    fun photoUserAvatar(username: String) = "user:$username:avatar"
    fun collectionCover(collectionId: String) = "collection:$collectionId:cover"
}
