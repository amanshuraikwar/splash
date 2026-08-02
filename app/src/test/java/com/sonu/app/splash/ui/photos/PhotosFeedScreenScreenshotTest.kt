package com.sonu.app.splash.ui.photos

import android.graphics.Bitmap
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
import com.sonu.app.splash.model.unsplash.Collection as UnsplashCollection
import com.sonu.app.splash.model.unsplash.Photo
import com.sonu.app.splash.model.unsplash.PhotoUrls
import com.sonu.app.splash.model.unsplash.ProfileImage
import com.sonu.app.splash.model.unsplash.User
import com.sonu.app.splash.ui.theme.PolygonTheme
import java.io.File
import java.net.URI
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
                PhotosFeedPagerScaffold(
                    modifier = Modifier.testTag(MAIN_FEED_TAG),
                    pages = listOf(PhotosFeedPage.AllPhotos, PhotosFeedPage.Collections),
                ) { page ->
                    when (page) {
                        PhotosFeedPage.AllPhotos -> PhotosFeedScreen(
                            state = PhotosFeedUiState(photos = photos),
                            onRetryClick = {},
                            onLoadMore = {},
                            onPhotoClick = {},
                            onImageSuccess = { photo ->
                                loadedPhotoIds.add(photo.id)
                            },
                            imageLoader = imageLoader,
                            imageCrossfade = false,
                            includeStatusBarPadding = false,
                        )

                        PhotosFeedPage.Collections -> CollectionsFeedScreen(
                            state = CollectionsFeedUiState(),
                            onRetryClick = {},
                            onLoadMore = {},
                            includeStatusBarPadding = false,
                        )
                    }
                }
            }
        }

        composeRule.waitUntil(timeoutMillis = 10_000) {
            loadedPhotoIds.containsAll(photos.map { it.id })
        }

        composeRule
            .onNodeWithTag(MAIN_FEED_TAG)
            .captureRoboImage("src/test/snapshots/photos_feed_main.png")
    }

    @Test
    fun recordsCollectionsScreen() {
        val collections = fixtureCollections()
        val loadedCollectionIds = Collections.synchronizedSet(mutableSetOf<Int>())
        val imageLoader = fixtureCollectionImageLoader(collections)

        composeRule.setContent {
            PolygonTheme {
                CollectionsFeedScreen(
                    state = CollectionsFeedUiState(collections = collections),
                    onRetryClick = {},
                    onLoadMore = {},
                    modifier = Modifier.testTag(COLLECTIONS_FEED_TAG),
                    onImageSuccess = { collection ->
                        loadedCollectionIds.add(collection.id)
                    },
                    imageLoader = imageLoader,
                    imageCrossfade = false,
                    includeStatusBarPadding = false,
                )
            }
        }

        composeRule.waitUntil(timeoutMillis = 10_000) {
            loadedCollectionIds.isNotEmpty()
        }

        composeRule
            .onNodeWithTag(COLLECTIONS_FEED_TAG)
            .captureRoboImage("src/test/snapshots/photos_feed_collections.png")
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

    private fun fixtureCollections(): List<UnsplashCollection> {
        val titles = listOf(
            "Soft Interiors",
            "Shadow Studies",
            "City Neon",
            "Forest Light",
            "Quiet Streets",
            "Warm Horizons",
        )
        val artists = listOf(
            "Nora Fields",
            "Mika Chen",
            "Eli Reyes",
            "June Park",
            "Ada Stone",
            "Kai Morgan",
        )
        val colors = listOf(
            "#90A4AE",
            "#6D4C41",
            "#455A64",
            "#78909C",
            "#5C6BC0",
            "#8D6E63",
        )
        val coverSizes = listOf(
            1400 to 860,
            1200 to 760,
            1300 to 900,
            1600 to 980,
            1500 to 930,
            1280 to 820,
        )

        return titles.mapIndexed { index, title ->
            UnsplashCollection.Builder(10_000 + index)
                .title(title)
                .totalPhotos((index + 2) * 18)
                .coverPhoto(
                    collectionCoverPhoto(
                        id = "collection-cover-$index",
                        color = colors[index],
                        width = coverSizes[index].first,
                        height = coverSizes[index].second,
                    ),
                )
                .user(
                    User.Builder("collection-user-$index")
                        .username(artists[index].lowercase().replace(" ", "_"))
                        .name(artists[index])
                        .profileImage(profileImage("fixture://collection-profile-$index"))
                        .build(),
                )
                .build()
        }
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

    private fun collectionCoverPhoto(
        id: String,
        color: String,
        width: Int,
        height: Int,
    ): Photo {
        val imageUri = "fixture://$id"
        return Photo.Builder(id)
            .width(width)
            .height(height)
            .color(color)
            .description("Collection cover")
            .urls(
                PhotoUrls.Builder()
                    .raw(imageUri)
                    .full(imageUri)
                    .regular(imageUri)
                    .small(imageUri)
                    .thumb(imageUri)
                    .build(),
            )
            .user(User.Builder("user-$id").name("Collection artist").build())
            .build()
    }

    private fun profileImage(imageUri: String): ProfileImage {
        return ProfileImage.Builder()
            .small(imageUri)
            .meduim(imageUri)
            .large(imageUri)
            .build()
    }

    private fun screenshotFixture(fileName: String): String {
        return File(projectRoot(), "screenshots/$fileName").toURI().toString()
    }

    private fun fixtureImageLoader(photos: List<Photo>): ImageLoader {
        val engineBuilder = FakeImageLoaderEngine.Builder()
        photos.forEach { photo ->
            engineBuilder.intercept(
                checkNotNull(photo.photoUrls.small),
                fixtureFileBitmap(checkNotNull(photo.photoUrls.small)).asImage(),
            )
        }

        return ImageLoader.Builder(RuntimeEnvironment.getApplication())
            .components { add(engineBuilder.build()) }
            .build()
    }

    private fun fixtureCollectionImageLoader(collections: List<UnsplashCollection>): ImageLoader {
        val engineBuilder = FakeImageLoaderEngine.Builder()
        collections.forEachIndexed { index, collection ->
            val coverPhoto = collection.coverPhoto
            coverPhoto?.photoUrls?.small?.let { imageUri ->
                engineBuilder.intercept(
                    imageUri,
                    fixtureColorBitmap(
                        width = coverPhoto.width,
                        height = coverPhoto.height,
                        color = COLLECTION_FIXTURE_COLORS[index],
                    ).asImage(),
                )
            }
            collection.user?.profileImage?.large?.let { imageUri ->
                engineBuilder.intercept(
                    imageUri,
                    fixtureColorBitmap(
                        width = 128,
                        height = 128,
                        color = PROFILE_FIXTURE_COLORS[index],
                    ).asImage(),
                )
            }
        }

        return ImageLoader.Builder(RuntimeEnvironment.getApplication())
            .components { add(engineBuilder.build()) }
            .build()
    }

    private fun fixtureFileBitmap(imageUri: String) = checkNotNull(
        BitmapFactory.decodeFile(File(URI.create(imageUri)).absolutePath),
    ) {
        "Unable to decode fixture image at $imageUri"
    }

    private fun fixtureColorBitmap(width: Int, height: Int, color: Int): Bitmap {
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
            eraseColor(color)
        }
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
        const val COLLECTIONS_FEED_TAG = "photos-feed-collections"
        val COLLECTION_FIXTURE_COLORS = intArrayOf(
            0xFF90A4AE.toInt(),
            0xFF6D4C41.toInt(),
            0xFF455A64.toInt(),
            0xFF78909C.toInt(),
            0xFF5C6BC0.toInt(),
            0xFF8D6E63.toInt(),
        )
        val PROFILE_FIXTURE_COLORS = intArrayOf(
            0xFF263238.toInt(),
            0xFF3E2723.toInt(),
            0xFF1A237E.toInt(),
            0xFF004D40.toInt(),
            0xFF311B92.toInt(),
            0xFF4E342E.toInt(),
        )
    }
}
