package com.sonu.app.splash.ui.photodescription

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import com.sonu.app.polygon.theme.PolygonTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [35], qualifiers = "w393dp-h180dp-xxhdpi")
class PhotoDescriptionScreenScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun recordsPhotoTitleMeshGradient() {
        composeRule.setContent {
            PolygonTheme {
                PhotoDescriptionTitlePreviewContent(
                    modifier = Modifier.testTag(PHOTO_TITLE_MESH_TAG),
                    animate = false,
                )
            }
        }

        composeRule
            .onNodeWithTag(PHOTO_TITLE_MESH_TAG)
            .assertIsDisplayed()
            .captureRoboImage("src/test/snapshots/photo_title_mesh_gradient.png")
    }

    private companion object {
        const val PHOTO_TITLE_MESH_TAG = "photo-title-mesh-gradient"
    }
}
