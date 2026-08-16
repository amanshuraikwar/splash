package com.sonu.app.polygon.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

object PolygonPalette {
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
data class PolygonColors(
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
    val info: Color,
)

val PolygonLightColors = PolygonColors(
    primary = PolygonPalette.DarkGrey3,
    primaryDark = PolygonPalette.DarkGrey3,
    accent = PolygonPalette.LightCyan,
    background = PolygonPalette.Grey1,
    surface = PolygonPalette.Grey1,
    surfaceElevated = PolygonPalette.White,
    statusBar = PolygonPalette.Grey1,
    navigationBar = PolygonPalette.DarkGrey3,
    primaryText = PolygonPalette.PrimaryText,
    secondaryText = PolygonPalette.SecondaryText,
    disabledText = PolygonPalette.DisabledHintText,
    divider = PolygonPalette.Divider,
    activeIcon = PolygonPalette.SecondaryText,
    inactiveIcon = PolygonPalette.DisabledHintText,
    activeIconLight = PolygonPalette.White,
    inactiveIconLight = PolygonPalette.DisabledHintTextLight,
    scrim = PolygonPalette.Scrim,
    success = PolygonPalette.Green,
    warning = PolygonPalette.Yellow,
    error = PolygonPalette.Red,
    info = PolygonPalette.LightBlueN,
)

val PolygonDarkColors = PolygonColors(
    primary = PolygonPalette.DarkGrey3,
    primaryDark = PolygonPalette.Black,
    accent = PolygonPalette.LightCyan,
    background = PolygonPalette.DarkGrey3,
    surface = PolygonPalette.DarkGrey2,
    surfaceElevated = PolygonPalette.DarkGrey1,
    statusBar = PolygonPalette.DarkGrey3,
    navigationBar = PolygonPalette.Black,
    primaryText = PolygonPalette.PrimaryTextLight,
    secondaryText = PolygonPalette.SecondaryTextLight,
    disabledText = PolygonPalette.DisabledHintTextLight,
    divider = PolygonPalette.DividerLight,
    activeIcon = PolygonPalette.White,
    inactiveIcon = PolygonPalette.DisabledHintTextLight,
    activeIconLight = PolygonPalette.White,
    inactiveIconLight = PolygonPalette.DisabledHintTextLight,
    scrim = PolygonPalette.Scrim,
    success = PolygonPalette.Green,
    warning = PolygonPalette.Yellow,
    error = PolygonPalette.Red,
    info = PolygonPalette.LightBlue,
)

val LocalPolygonColors = staticCompositionLocalOf { PolygonLightColors }
val LocalPolygonContentColor = staticCompositionLocalOf { PolygonLightColors.primaryText }
