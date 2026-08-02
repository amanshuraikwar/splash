package com.sonu.app.splash.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

object SplashPalette {
    val White = Color(0xFFFFFFFF)
    val Grey1 = Color(0xFFFAFAFA)
    val Grey2 = Color(0xFFF5F5F5)
    val Grey3 = Color(0xFFE0E0E0)
    val Grey1Trans = Color(0xBBFAFAFA)

    val DarkGrey1 = Color(0xFF424242)
    val DarkGrey2 = Color(0xFF303030)
    val DarkGrey3 = Color(0xFF212121)
    val Black = Color(0xFF000000)
    val DarkGrey1Trans = Color(0x55424242)

    val PrimaryText = Color(0xEE000000)
    val SecondaryText = Color(0x8A000000)
    val DisabledHintText = Color(0x61000000)
    val Divider = Color(0x1F000000)

    val PrimaryTextLight = Color(0xFFFFFFFF)
    val SecondaryTextLight = Color(0xB3FFFFFF)
    val DisabledHintTextLight = Color(0x80FFFFFF)
    val DividerLight = Color(0x1FFFFFFF)

    val Scrim = Color(0x99323232)
    val LightCyan = Color(0xFF84FFFF)
    val LightBlue = Color(0xFF80DEEA)
    val Sky = Color(0xFFB2EBF2)
    val Green = Color(0xFF66BB6A)
    val DarkRed = Color(0xFFB71C1C)
    val MidRed = Color(0xFFEF5350)
    val Red = Color(0xFFF44336)
    val Orange = Color(0xFFFF9800)
    val DarkOrange = Color(0xFFE65100)
    val LightBlueN = Color(0xFF03A9F4)
    val Pink = Color(0xFFE91E63)
    val Blue = Color(0xFF2196F3)
    val Teal = Color(0xFF009688)
    val Indigo = Color(0xFF3F51B5)
    val Yellow = Color(0xFFFFEB3B)
}

@Immutable
data class SplashColorTokens(
    val primary: Color,
    val primaryDark: Color,
    val accent: Color,
    val background: Color,
    val surface: Color,
    val surfaceElevated: Color,
    val statusBar: Color,
    val navigationBar: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val disabledText: Color,
    val divider: Color,
    val activeIcon: Color,
    val inactiveIcon: Color,
    val activeIconLight: Color,
    val inactiveIconLight: Color,
    val scrim: Color,
    val success: Color,
    val warning: Color,
    val error: Color,
    val info: Color
)

val SplashLightColors = SplashColorTokens(
    primary = SplashPalette.DarkGrey3,
    primaryDark = SplashPalette.DarkGrey3,
    accent = SplashPalette.LightCyan,
    background = SplashPalette.Grey1,
    surface = SplashPalette.Grey1,
    surfaceElevated = SplashPalette.White,
    statusBar = SplashPalette.Grey1,
    navigationBar = SplashPalette.DarkGrey3,
    primaryText = SplashPalette.PrimaryText,
    secondaryText = SplashPalette.SecondaryText,
    disabledText = SplashPalette.DisabledHintText,
    divider = SplashPalette.Divider,
    activeIcon = SplashPalette.SecondaryText,
    inactiveIcon = SplashPalette.DisabledHintText,
    activeIconLight = SplashPalette.White,
    inactiveIconLight = SplashPalette.DisabledHintTextLight,
    scrim = SplashPalette.Scrim,
    success = SplashPalette.Green,
    warning = SplashPalette.Yellow,
    error = SplashPalette.Red,
    info = SplashPalette.LightBlueN
)

val SplashDarkColors = SplashColorTokens(
    primary = SplashPalette.DarkGrey3,
    primaryDark = SplashPalette.Black,
    accent = SplashPalette.LightCyan,
    background = SplashPalette.DarkGrey3,
    surface = SplashPalette.DarkGrey2,
    surfaceElevated = SplashPalette.DarkGrey1,
    statusBar = SplashPalette.DarkGrey3,
    navigationBar = SplashPalette.Black,
    primaryText = SplashPalette.PrimaryTextLight,
    secondaryText = SplashPalette.SecondaryTextLight,
    disabledText = SplashPalette.DisabledHintTextLight,
    divider = SplashPalette.DividerLight,
    activeIcon = SplashPalette.White,
    inactiveIcon = SplashPalette.DisabledHintTextLight,
    activeIconLight = SplashPalette.White,
    inactiveIconLight = SplashPalette.DisabledHintTextLight,
    scrim = SplashPalette.Scrim,
    success = SplashPalette.Green,
    warning = SplashPalette.Yellow,
    error = SplashPalette.Red,
    info = SplashPalette.LightBlue
)

val LocalSplashColors = staticCompositionLocalOf { SplashLightColors }
