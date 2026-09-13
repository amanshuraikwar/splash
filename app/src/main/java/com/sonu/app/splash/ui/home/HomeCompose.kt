package com.sonu.app.splash.ui.home

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sonu.app.splash.data.download.Downloader
import com.sonu.app.splash.data.local.LocalStore
import com.sonu.app.splash.data.media.CollectionRepository
import com.sonu.app.splash.data.media.MediaRepository
import com.sonu.app.splash.data.rss.RssRepository
import com.sonu.app.splash.ui.SplashApp
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionRoute
import com.sonu.app.splash.ui.photos.MediaFeedRoute
import com.sonu.app.splash.ui.rss.RssDetailRoute

object HomeCompose {
    @JvmStatic
    fun setContent(
        activity: ComponentActivity,
        mediaRepository: MediaRepository,
        collectionRepository: CollectionRepository,
        rssRepository: RssRepository,
        localStore: LocalStore,
        downloader: Downloader,
    ) {
        activity.setContent {
            SplashApp(
                home = {
                    MediaFeedRoute(
                        mediaRepository = mediaRepository,
                        collectionRepository = collectionRepository,
                        rssRepository = rssRepository,
                        destinationScope = this,
                    )
                },
                photoDescription = { route ->
                    PhotoDescriptionRoute(
                        mediaRepository = mediaRepository,
                        localStore = localStore,
                        downloader = downloader,
                        route = route,
                        destinationScope = this,
                    )
                },
                rssDetail = { route ->
                    RssDetailRoute(
                        route = route,
                        destinationScope = this,
                    )
                },
            )
        }
    }
}
