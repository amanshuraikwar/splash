package com.sonu.app.splash.ui.photos

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil3.ImageLoader
import coil3.asImage
import coil3.test.FakeImageLoaderEngine
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RoboVideoOptions
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.takahirom.roborazzi.provideRoborazziContext
import com.github.takahirom.roborazzi.recordScreenRoboVideo
import com.sonu.app.splash.model.unsplash.Collection as UnsplashCollection
import com.sonu.app.splash.model.unsplash.Photo
import com.sonu.app.splash.model.unsplash.PhotoUrls
import com.sonu.app.splash.model.unsplash.ProfileImage
import com.sonu.app.splash.model.unsplash.User
import com.sonu.app.splash.ui.navigation.SplashNavDisplay
import com.sonu.app.splash.ui.navigation.SplashRoute
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionScreen
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionUiState
import com.sonu.app.splash.ui.photodescription.toPreview
import com.sonu.app.splash.ui.theme.PolygonTheme
import java.io.File
import java.net.URI
import java.util.Collections
import javax.imageio.ImageIO
import org.junit.Assert.assertTrue
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
        val loadedCollectionIds = Collections.synchronizedSet(mutableSetOf<String>())
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

    @OptIn(ExperimentalRoborazziApi::class)
    @Test
    fun recordsPhotoDetailsSharedTransition() {
        val photos = transitionFixturePhotos()
        val selectedPhoto = photos.first()
        val selectedPhotoId = checkNotNull(selectedPhoto.id)
        val loadedPhotoIds = Collections.synchronizedSet(mutableSetOf<String>())
        val imageLoader = fixtureTransitionImageLoader(photos)
        val recordingFile = File(
            projectRoot(),
            "app/build/outputs/roborazzi/photos_feed_to_detail_shared_transition.gif",
        )

        composeRule.setContent {
            PolygonTheme {
                SplashNavDisplay(
                    home = {
                        PhotosFeedPagerScaffold(
                            pages = listOf(PhotosFeedPage.AllPhotos, PhotosFeedPage.Collections),
                        ) { page ->
                            when (page) {
                                PhotosFeedPage.AllPhotos -> PhotosFeedScreen(
                                    state = PhotosFeedUiState(photos = photos),
                                    onRetryClick = {},
                                    onLoadMore = {},
                                    onPhotoClick = { photo ->
                                        navigate(SplashRoute.PhotoDescription.fromPhoto(photo))
                                    },
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
                    },
                    photoDescription = { route ->
                        val photo = photos.first { it.id == route.photoId }

                        PhotoDescriptionScreen(
                            state = PhotoDescriptionUiState(
                                photoId = route.photoId,
                                preview = route.toPreview(),
                                photo = photo,
                            ),
                            onBackClick = { popBackStack() },
                            onRetryClick = {},
                            modifier = Modifier.testTag(PHOTO_DETAIL_TAG),
                            imageLoader = imageLoader,
                            imageCrossfade = false,
                        )
                    },
                )
            }
        }

        composeRule.waitUntil(timeoutMillis = 10_000) {
            loadedPhotoIds.containsAll(photos.map { it.id })
        }

        if (provideRoborazziContext().options.taskType.isRecording()) {
            recordScreenRoboVideo(
                composeRule = composeRule,
                file = recordingFile,
                videoOptions = RoboVideoOptions(
                    fps = 20,
                    settleTimeoutMillis = 1_000,
                ),
            ) {
                composeRule
                    .onNodeWithTag(PhotosFeedTestTags.photoCard(selectedPhotoId))
                    .performClick()
                delay(700)
            }

            assertAnimatedGif(recordingFile)
        } else {
            composeRule
                .onNodeWithTag(PhotosFeedTestTags.photoCard(selectedPhotoId))
                .performClick()
            composeRule.mainClock.advanceTimeBy(700)
        }

        composeRule.onNodeWithTag(PHOTO_DETAIL_TAG).assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Back").assertIsDisplayed()
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
            UnsplashCollection.Builder("collection-$index")
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

    private fun transitionFixturePhotos(): List<Photo> {
        return listOf(
            transitionPhoto(
                id = "transition-photo-1",
                color = "#263238",
                width = 1200,
                height = 1600,
                photographer = "Nora Fields",
            ),
            transitionPhoto(
                id = "transition-photo-2",
                color = "#5D4037",
                width = 1400,
                height = 900,
                photographer = "Mika Chen",
            ),
            transitionPhoto(
                id = "transition-photo-3",
                color = "#455A64",
                width = 900,
                height = 1350,
                photographer = "Eli Reyes",
            ),
            transitionPhoto(
                id = "transition-photo-4",
                color = "#6A1B9A",
                width = 1600,
                height = 1100,
                photographer = "June Park",
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

    private fun transitionPhoto(
        id: String,
        color: String,
        width: Int,
        height: Int,
        photographer: String,
    ): Photo {
        val imageUri = "fixture://$id"
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

    private fun fixtureTransitionImageLoader(photos: List<Photo>): ImageLoader {
        val engineBuilder = FakeImageLoaderEngine.Builder()
        photos.forEachIndexed { index, photo ->
            val imageUri = checkNotNull(photo.photoUrls.small)
            val gradient = TRANSITION_FIXTURE_GRADIENTS[index]
            engineBuilder.intercept(
                imageUri,
                fixtureGradientBitmap(
                    width = photo.width,
                    height = photo.height,
                    startColor = gradient.first,
                    endColor = gradient.second,
                ).asImage(),
            )
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

    private fun fixtureGradientBitmap(width: Int, height: Int, startColor: Int, endColor: Int): Bitmap {
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
            val paint = Paint().apply {
                shader = LinearGradient(
                    0f,
                    0f,
                    width.toFloat(),
                    height.toFloat(),
                    startColor,
                    endColor,
                    Shader.TileMode.CLAMP,
                )
            }
            Canvas(this).drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
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

    private fun assertAnimatedGif(file: File) {
        assertTrue("Expected Roborazzi GIF to exist at ${file.absolutePath}", file.isFile)
        assertTrue("Expected Roborazzi GIF to be non-empty", file.length() > 0L)

        val reader = ImageIO.getImageReadersByFormatName("gif").next()
        val imageInputStream = ImageIO.createImageInputStream(file)
        try {
            reader.input = imageInputStream
            assertTrue(
                "Expected transition GIF to contain multiple frames",
                reader.getNumImages(true) > 3,
            )
        } finally {
            reader.dispose()
            imageInputStream.close()
        }
    }

    private companion object {
        const val MAIN_FEED_TAG = "photos-feed-main"
        const val COLLECTIONS_FEED_TAG = "photos-feed-collections"
        const val PHOTO_DETAIL_TAG = "photo-detail"
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
        val TRANSITION_FIXTURE_GRADIENTS = arrayOf(
            0xFF263238.toInt() to 0xFFFFC107.toInt(),
            0xFF1B5E20.toInt() to 0xFF64B5F6.toInt(),
            0xFF4A148C.toInt() to 0xFFFF8A65.toInt(),
            0xFF006064.toInt() to 0xFFFFD54F.toInt(),
        )
    }
}
