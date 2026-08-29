package com.sonu.app.splash.ui.navigation

import com.sonu.app.splash.model.unsplash.Photo
import com.sonu.app.splash.data.rss.model.RssItem
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface SplashRoute : NavKey {
    @Serializable
    data object Home : SplashRoute

    @Serializable
    data object About : SplashRoute

    @Serializable
    data class PhotoDescription(
        val photoId: String,
        val imageUrl: String? = null,
        val width: Int = 0,
        val height: Int = 0,
        val color: String? = null,
        val description: String? = null,
        val userName: String? = null,
        val username: String? = null,
        val userAvatarUrl: String? = null,
    ) : SplashRoute {
        companion object {
            fun fromPhoto(photo: Photo): PhotoDescription {
                val photoUrls = photo.photoUrls
                val user = photo.user
                val profileImage = user?.profileImage

                return PhotoDescription(
                    photoId = photo.id.orEmpty(),
                    imageUrl = photoUrls?.small
                        ?: photoUrls?.regular
                        ?: photoUrls?.full
                        ?: photoUrls?.thumb,
                    width = photo.width,
                    height = photo.height,
                    color = photo.color,
                    description = photo.description,
                    userName = user?.name,
                    username = user?.username,
                    userAvatarUrl = profileImage?.large
                        ?: profileImage?.meduim
                        ?: profileImage?.small,
                )
            }
        }
    }

    @Serializable
    data class RssDetail(
        val itemId: String,
        val feedTitle: String,
        val feedUrl: String,
        val title: String,
        val link: String? = null,
        val description: String? = null,
        val imageUrl: String? = null,
        val publishedAtEpochMillis: Long? = null,
    ) : SplashRoute {
        companion object {
            fun fromItem(item: RssItem): RssDetail {
                return RssDetail(
                    itemId = item.id,
                    feedTitle = item.feedTitle,
                    feedUrl = item.feedUrl,
                    title = item.title,
                    link = item.link,
                    description = item.description,
                    imageUrl = item.imageUrl,
                    publishedAtEpochMillis = item.publishedAtEpochMillis,
                )
            }
        }
    }

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
    const val photosTopChrome = "photos:top-chrome"

    fun photoSurface(photoId: String) = "photo:$photoId:surface"
    fun photoImage(photoId: String) = "photo:$photoId:image"
    fun photoImageMemoryCache(photoId: String) = "photo:$photoId:image:memory"
    fun rssSurface(itemId: String) = "rss:$itemId:surface"
    fun rssImage(itemId: String) = "rss:$itemId:image"
    fun rssImageMemoryCache(itemId: String) = "rss:$itemId:image:memory"
    fun photoUserAvatar(username: String) = "user:$username:avatar"
    fun collectionCover(collectionId: String) = "collection:$collectionId:cover"
}
