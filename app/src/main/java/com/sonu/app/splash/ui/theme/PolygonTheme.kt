package com.sonu.app.splash.ui.theme

import android.graphics.Color.TRANSPARENT as TransparentColor
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView

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
        PolygonSystemBars(darkTheme = darkTheme)
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
private fun PolygonSystemBars(darkTheme: Boolean) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context as? ComponentActivity ?: return@SideEffect
            val systemBarStyle = if (darkTheme) {
                SystemBarStyle.dark(TransparentColor)
            } else {
                SystemBarStyle.light(TransparentColor, TransparentColor)
            }
            activity.enableEdgeToEdge(
                statusBarStyle = systemBarStyle,
                navigationBarStyle = systemBarStyle,
            )
        }
    }
}
