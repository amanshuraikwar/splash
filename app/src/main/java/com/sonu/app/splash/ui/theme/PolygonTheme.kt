package com.sonu.app.splash.ui.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun PolygonTheme(
    darkTheme: Boolean = false,
    applySystemBars: Boolean = false,
    colors: PolygonColors = if (darkTheme) PolygonDarkColors else PolygonLightColors,
    typography: PolygonTypography = PolygonDefaultTypography,
    dimensions: PolygonDimensions = PolygonDefaultDimensions,
    shapes: PolygonShapes = PolygonDefaultShapes,
    elevation: PolygonElevation = PolygonDefaultElevation,
    motion: PolygonMotion = PolygonDefaultMotion,
    contentColor: Color = colors.primaryText,
    content: @Composable () -> Unit,
) {
    if (applySystemBars) {
        PolygonSystemBars(colors = colors, darkTheme = darkTheme)
    }

    CompositionLocalProvider(
        LocalPolygonColors provides colors,
        LocalPolygonTypography provides typography,
        LocalPolygonDimensions provides dimensions,
        LocalPolygonShapes provides shapes,
        LocalPolygonElevation provides elevation,
        LocalPolygonMotion provides motion,
        LocalPolygonContentColor provides contentColor,
        content = content,
    )
}

object Polygon {
    val colors: PolygonColors
        @Composable
        @ReadOnlyComposable
        get() = LocalPolygonColors.current

    val typography: PolygonTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalPolygonTypography.current

    val dimensions: PolygonDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalPolygonDimensions.current

    val shapes: PolygonShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalPolygonShapes.current

    val elevation: PolygonElevation
        @Composable
        @ReadOnlyComposable
        get() = LocalPolygonElevation.current

    val motion: PolygonMotion
        @Composable
        @ReadOnlyComposable
        get() = LocalPolygonMotion.current

    val contentColor: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalPolygonContentColor.current
}

@Composable
private fun PolygonSystemBars(colors: PolygonColors, darkTheme: Boolean) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            window.statusBarColor = colors.statusBar.toArgb()
            window.navigationBarColor = colors.navigationBar.toArgb()

            val controller = WindowCompat.getInsetsController(window, view)
            controller.isAppearanceLightStatusBars = !darkTheme
            controller.isAppearanceLightNavigationBars = false
        }
    }
}
