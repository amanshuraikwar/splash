package com.sonu.app.splash.ui.photos

import android.graphics.BitmapFactory
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil3.ImageLoader
import coil3.asImage
import coil3.test.FakeImageLoaderEngine
import com.github.takahirom.roborazzi.captureRoboImage
import com.sonu.app.splash.model.unsplash.Photo
import com.sonu.app.splash.model.unsplash.PhotoUrls
import com.sonu.app.splash.model.unsplash.User
import com.sonu.app.splash.ui.theme.PolygonTheme
import java.io.File
import java.util.Collections
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [35], qualifiers = "w393dp-h851dp-xxhdpi")
class PhotosFeedScreenScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun recordsMainScreen() {
        val photos = fixturePhotos()
        val loadedPhotoIds = Collections.synchronizedSet(mutableSetOf<String>())
        val imageLoader = fixtureImageLoader(photos)

        composeRule.setContent {
            PolygonTheme {
                PhotosFeedScreen(
                    state = PhotosFeedUiState(photos = photos),
                    onRetryClick = {},
                    onLoadMore = {},
                    onPhotoClick = {},
                    onDownloadClick = {},
                    modifier = Modifier.testTag(MAIN_FEED_TAG),
                    onImageSuccess = { photo ->
                        loadedPhotoIds.add(photo.id)
                    },
                    imageLoader = imageLoader,
                    imageCrossfade = false,
                )
            }
        }

        composeRule.waitUntil(timeoutMillis = 10_000) {
            loadedPhotoIds.containsAll(photos.map { it.id })
        }

        composeRule
            .onNodeWithTag(MAIN_FEED_TAG)
            .captureRoboImage("src/test/snapshots/photos_feed_main.png")
    }

    private fun fixturePhotos(): List<Photo> {
        return listOf(
            photo(
                id = "photo-1",
                color = "#424242",
                width = 1200,
                height = 1600,
                photographer = "Nora Fields",
            ),
            photo(
                id = "photo-2",
                color = "#303030",
                width = 1400,
                height = 900,
                photographer = "Mika Chen",
            ),
            photo(
                id = "photo-3",
                color = "#212121",
                width = 900,
                height = 1350,
                photographer = "Eli Reyes",
            ),
            photo(
                id = "photo-4",
                color = "#616161",
                width = 1600,
                height = 1100,
                photographer = "June Park",
            ),
            photo(
                id = "photo-5",
                color = "#455A64",
                width = 1100,
                height = 1400,
                photographer = "Ada Stone",
            ),
            photo(
                id = "photo-6",
                color = "#546E7A",
                width = 1400,
                height = 1000,
                photographer = "Kai Morgan",
            ),
        )
    }

    private fun photo(
        id: String,
        color: String,
        width: Int,
        height: Int,
        photographer: String,
    ): Photo {
        val imageUri = screenshotFixture(fixtureImageName(id))
        return Photo.Builder(id)
            .width(width)
            .height(height)
            .color(color)
            .description("Photo by $photographer")
            .urls(
                PhotoUrls.Builder()
                    .raw(imageUri)
                    .full(imageUri)
                    .regular(imageUri)
                    .small(imageUri)
                    .thumb(imageUri)
                    .build(),
            )
            .user(
                User.Builder("user-$id")
                    .username(photographer.lowercase().replace(" ", "_"))
                    .name(photographer)
                    .build(),
            )
            .build()
    }

    private fun screenshotFixture(fileName: String): String {
        return File(projectRoot(), "screenshots/$fileName").toURI().toString()
    }

    private fun fixtureImageLoader(photos: List<Photo>): ImageLoader {
        val engineBuilder = FakeImageLoaderEngine.Builder()
        photos.forEach { photo ->
            val bitmap = checkNotNull(
                BitmapFactory.decodeFile(
                    File(projectRoot(), "screenshots/${fixtureImageName(photo.id)}").absolutePath,
                ),
            ) {
                "Unable to decode fixture image for ${photo.id}"
            }
            engineBuilder.intercept(
                checkNotNull(photo.photoUrls.small),
                bitmap.asImage(),
            )
        }

        return ImageLoader.Builder(RuntimeEnvironment.getApplication())
            .components { add(engineBuilder.build()) }
            .build()
    }

    private fun fixtureImageName(photoId: String): String {
        return when (photoId) {
            "photo-1" -> "ss-home-1.jpg"
            "photo-2" -> "ss-collections-1.jpg"
            "photo-3" -> "ss-user-desc-1.jpg"
            "photo-4" -> "ss-search-1.jpg"
            "photo-5" -> "ss-photo-desc-1.jpg"
            "photo-6" -> "ss-downloads.jpg"
            else -> error("Missing fixture for $photoId")
        }
    }

    private fun projectRoot(): File {
        return generateSequence(File(requireNotNull(System.getProperty("user.dir")))) { it.parentFile }
            .first { File(it, "settings.gradle").exists() }
    }

    private companion object {
        const val MAIN_FEED_TAG = "photos-feed-main"
    }
}
