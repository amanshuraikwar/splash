package com.sonu.app.splash.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val SplashLightMaterialColors = lightColorScheme(
    primary = SplashPalette.DarkGrey3,
    onPrimary = SplashPalette.PrimaryTextLight,
    primaryContainer = SplashPalette.DarkGrey1,
    onPrimaryContainer = SplashPalette.PrimaryTextLight,
    secondary = SplashPalette.LightCyan,
    onSecondary = SplashPalette.PrimaryText,
    background = SplashPalette.Grey1,
    onBackground = SplashPalette.PrimaryText,
    surface = SplashPalette.Grey1,
    onSurface = SplashPalette.PrimaryText,
    surfaceVariant = SplashPalette.Grey2,
    onSurfaceVariant = SplashPalette.SecondaryText,
    outline = SplashPalette.Divider,
    error = SplashPalette.Red,
    onError = SplashPalette.PrimaryTextLight
)

private val SplashDarkMaterialColors = darkColorScheme(
    primary = SplashPalette.LightCyan,
    onPrimary = SplashPalette.PrimaryText,
    primaryContainer = SplashPalette.DarkGrey1,
    onPrimaryContainer = SplashPalette.PrimaryTextLight,
    secondary = SplashPalette.LightBlue,
    onSecondary = SplashPalette.PrimaryText,
    background = SplashPalette.DarkGrey3,
    onBackground = SplashPalette.PrimaryTextLight,
    surface = SplashPalette.DarkGrey2,
    onSurface = SplashPalette.PrimaryTextLight,
    surfaceVariant = SplashPalette.DarkGrey1,
    onSurfaceVariant = SplashPalette.SecondaryTextLight,
    outline = SplashPalette.DividerLight,
    error = SplashPalette.Red,
    onError = SplashPalette.PrimaryTextLight
)

@Composable
fun SplashTheme(
    darkTheme: Boolean = false,
    applySystemBars: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) SplashDarkColors else SplashLightColors
    val materialColors = if (darkTheme) SplashDarkMaterialColors else SplashLightMaterialColors

    if (applySystemBars) {
        SplashSystemBars(colors = colors, darkTheme = darkTheme)
    }

    CompositionLocalProvider(
        LocalSplashColors provides colors,
        LocalSplashDimensions provides SplashDefaultDimensions,
        LocalSplashTextStyles provides SplashDefaultTextStyles
    ) {
        MaterialTheme(
            colorScheme = materialColors,
            typography = SplashMaterialTypography,
            content = content
        )
    }
}

object SplashThemeTokens {
    val colors: SplashColorTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalSplashColors.current

    val dimensions: SplashDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalSplashDimensions.current

    val textStyles: SplashTextStyles
        @Composable
        @ReadOnlyComposable
        get() = LocalSplashTextStyles.current
}

@Composable
private fun SplashSystemBars(colors: SplashColorTokens, darkTheme: Boolean) {
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
