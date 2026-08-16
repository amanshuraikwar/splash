package com.sonu.app.polygon.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

object PolygonFontFamilies {
    val Sans = FontFamily.SansSerif
    val Mono = FontFamily.Monospace
    val Condensed = FontFamily.SansSerif
}

object PolygonTypeScale {
    val displayLarge = TextStyle(
        fontFamily = PolygonFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 112.sp,
    )
    val displayMedium = TextStyle(
        fontFamily = PolygonFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 56.sp,
    )
    val displaySmall = TextStyle(
        fontFamily = PolygonFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
    )
    val headlineLarge = TextStyle(
        fontFamily = PolygonFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
    )
    val headlineMedium = TextStyle(
        fontFamily = PolygonFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
    )
    val titleLarge = TextStyle(
        fontFamily = PolygonFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    )
    val titleMedium = TextStyle(
        fontFamily = PolygonFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    )
    val bodyLarge = TextStyle(
        fontFamily = PolygonFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    )
    val bodyMedium = TextStyle(
        fontFamily = PolygonFontFamilies.Sans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    )
    val labelLarge = TextStyle(
        fontFamily = PolygonFontFamilies.Sans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    )
    val labelSmall = TextStyle(
        fontFamily = PolygonFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    )
}

@Immutable
data class PolygonTypography(
    val displayLarge: TextStyle,
    val displayMedium: TextStyle,
    val displaySmall: TextStyle,
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val labelLarge: TextStyle,
    val labelSmall: TextStyle,
    val toolbarTitleSmallCaps: TextStyle,
    val toolbarTitleBlack: TextStyle,
    val persistentMessage: TextStyle,
    val appNameBig: TextStyle,
    val appShortDescription: TextStyle,
    val appInfoHeading: TextStyle,
    val appInfoElementTitle: TextStyle,
    val appInfoElementSubtitle: TextStyle,
    val locationTitle: TextStyle,
    val photoDescription: TextStyle,
    val photoStatsValue: TextStyle,
    val artistName: TextStyle,
    val artistNameLight: TextStyle,
    val artistUsername: TextStyle,
    val artistNameBig: TextStyle,
    val artistUsernameBig: TextStyle,
    val artistBio: TextStyle,
    val errorTitle: TextStyle,
    val errorTitleLight: TextStyle,
    val errorMessage: TextStyle,
    val errorMessageLight: TextStyle,
    val button: TextStyle,
    val buttonSmall: TextStyle,
    val buttonLight: TextStyle,
    val artistTag: TextStyle,
    val filter: TextStyle,
    val header: TextStyle,
    val largeHeader: TextStyle,
    val overlayLarge: TextStyle,
    val collectionPhotoCount: TextStyle,
    val collectionTitle: TextStyle,
    val collectionTitleDark: TextStyle,
    val collectionTitleLarge: TextStyle,
    val fileName: TextStyle,
    val fileSize: TextStyle,
    val tab: TextStyle,
    val downloadStatus: TextStyle,
    val downloadTimestamp: TextStyle,
    val searchQuery: TextStyle,
    val photoStatsChange: TextStyle,
    val photoStatsChangeLight: TextStyle,
    val photoStatsChangeText: TextStyle,
    val photoStatsChangeTextLight: TextStyle,
    val photoStatsLimits: TextStyle,
    val photoStatsLimitsLight: TextStyle,
    val favDate: TextStyle,
)

val PolygonDefaultTypography = PolygonTypography(
    displayLarge = PolygonTypeScale.displayLarge,
    displayMedium = PolygonTypeScale.displayMedium,
    displaySmall = PolygonTypeScale.displaySmall,
    headlineLarge = PolygonTypeScale.headlineLarge,
    headlineMedium = PolygonTypeScale.headlineMedium,
    titleLarge = PolygonTypeScale.titleLarge,
    titleMedium = PolygonTypeScale.titleMedium,
    bodyLarge = PolygonTypeScale.bodyLarge,
    bodyMedium = PolygonTypeScale.bodyMedium,
    labelLarge = PolygonTypeScale.labelLarge,
    labelSmall = PolygonTypeScale.labelSmall,
    toolbarTitleSmallCaps = PolygonTypeScale.titleLarge.copy(
        color = PolygonPalette.PrimaryText,
        fontFeatureSettings = "smcp",
        letterSpacing = 0.2.em,
    ),
    toolbarTitleBlack = PolygonTypeScale.titleLarge.copy(
        color = PolygonPalette.PrimaryText,
        fontWeight = FontWeight.Black,
        fontSize = 18.sp,
    ),
    persistentMessage = PolygonTypeScale.labelSmall.copy(
        color = PolygonPalette.PrimaryTextLight,
        fontWeight = FontWeight.Black,
    ),
    appNameBig = PolygonTypeScale.displayMedium.copy(
        color = PolygonPalette.SecondaryText,
        fontWeight = FontWeight.Medium,
        fontFeatureSettings = "smcp",
        letterSpacing = 0.2.em,
    ),
    appShortDescription = PolygonTypeScale.labelSmall.copy(
        color = PolygonPalette.DisabledHintText,
        fontWeight = FontWeight.Medium,
        fontFeatureSettings = "smcp",
    ),
    appInfoHeading = PolygonTypeScale.bodyMedium.copy(
        color = PolygonPalette.DisabledHintText,
        fontFamily = PolygonFontFamilies.Condensed,
        fontWeight = FontWeight.Bold,
    ),
    appInfoElementTitle = PolygonTypeScale.titleMedium.copy(
        color = PolygonPalette.PrimaryText,
        fontWeight = FontWeight.Medium,
    ),
    appInfoElementSubtitle = PolygonTypeScale.bodyLarge.copy(
        color = PolygonPalette.SecondaryText,
    ),
    locationTitle = PolygonTypeScale.bodyMedium.copy(
        color = PolygonPalette.SecondaryText,
        fontFamily = PolygonFontFamilies.Condensed,
        fontWeight = FontWeight.Bold,
    ),
    photoDescription = PolygonTypeScale.headlineMedium.copy(
        color = PolygonPalette.PrimaryText,
        fontFamily = PolygonFontFamilies.Mono,
    ),
    photoStatsValue = PolygonTypeScale.labelSmall.copy(
        color = PolygonPalette.SecondaryText,
        fontWeight = FontWeight.Medium,
        fontFeatureSettings = "smcp, onum",
        fontSize = 14.sp,
        letterSpacing = 0.02.em,
    ),
    artistName = PolygonTypeScale.bodyLarge.copy(
        color = PolygonPalette.PrimaryText,
        fontWeight = FontWeight.Medium,
        fontFeatureSettings = "smcp, onum",
        letterSpacing = 0.02.em,
    ),
    artistNameLight = PolygonTypeScale.bodyLarge.copy(
        color = PolygonPalette.PrimaryTextLight,
        fontWeight = FontWeight.Medium,
        fontFeatureSettings = "smcp, onum",
        letterSpacing = 0.02.em,
    ),
    artistUsername = PolygonTypeScale.labelSmall.copy(
        color = PolygonPalette.DisabledHintText,
        fontFamily = PolygonFontFamilies.Mono,
        fontWeight = FontWeight.Medium,
    ),
    artistNameBig = PolygonTypeScale.titleLarge.copy(
        color = PolygonPalette.PrimaryText,
        fontWeight = FontWeight.Medium,
        fontFeatureSettings = "smcp, onum",
        fontSize = 20.sp,
        letterSpacing = 0.02.em,
    ),
    artistUsernameBig = PolygonTypeScale.bodyLarge.copy(
        color = PolygonPalette.DisabledHintText,
        fontFamily = PolygonFontFamilies.Mono,
        fontWeight = FontWeight.Medium,
    ),
    artistBio = PolygonTypeScale.titleMedium.copy(
        color = PolygonPalette.SecondaryText,
        fontFamily = PolygonFontFamilies.Mono,
    ),
    errorTitle = PolygonTypeScale.titleLarge.copy(
        color = PolygonPalette.PrimaryText,
        fontWeight = FontWeight.Bold,
        fontFeatureSettings = "smcp",
    ),
    errorTitleLight = PolygonTypeScale.titleLarge.copy(
        color = PolygonPalette.PrimaryTextLight,
        fontWeight = FontWeight.Bold,
        fontFeatureSettings = "smcp",
    ),
    errorMessage = PolygonTypeScale.bodyMedium.copy(
        color = PolygonPalette.SecondaryText,
    ),
    errorMessageLight = PolygonTypeScale.bodyMedium.copy(
        color = PolygonPalette.SecondaryTextLight,
    ),
    button = PolygonTypeScale.labelLarge.copy(
        color = PolygonPalette.PrimaryText,
        fontFamily = PolygonFontFamilies.Mono,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.1.em,
    ),
    buttonSmall = PolygonTypeScale.labelLarge.copy(
        color = PolygonPalette.PrimaryText,
        fontFamily = PolygonFontFamilies.Mono,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        letterSpacing = 0.1.em,
    ),
    buttonLight = PolygonTypeScale.labelLarge.copy(
        color = PolygonPalette.PrimaryTextLight,
        fontFamily = PolygonFontFamilies.Mono,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.1.em,
    ),
    artistTag = PolygonTypeScale.bodyMedium.copy(
        color = PolygonPalette.DisabledHintText,
        fontFamily = PolygonFontFamilies.Condensed,
    ),
    filter = PolygonTypeScale.titleMedium.copy(
        color = PolygonPalette.SecondaryText,
        fontWeight = FontWeight.Medium,
    ),
    header = PolygonTypeScale.bodyMedium.copy(
        color = PolygonPalette.DisabledHintText,
        fontFamily = PolygonFontFamilies.Condensed,
        fontWeight = FontWeight.Bold,
    ),
    largeHeader = PolygonTypeScale.titleLarge.copy(
        color = PolygonPalette.DisabledHintText,
        fontFamily = PolygonFontFamilies.Condensed,
        fontWeight = FontWeight.Bold,
    ),
    overlayLarge = PolygonTypeScale.headlineLarge.copy(
        color = PolygonPalette.SecondaryText,
        fontFamily = PolygonFontFamilies.Condensed,
        fontWeight = FontWeight.Bold,
    ),
    collectionPhotoCount = PolygonTypeScale.bodyLarge.copy(
        color = PolygonPalette.SecondaryTextLight,
        fontWeight = FontWeight.Bold,
        fontFeatureSettings = "smcp, onum",
    ),
    collectionTitle = PolygonTypeScale.titleLarge.copy(
        color = PolygonPalette.PrimaryTextLight,
        fontFamily = PolygonFontFamilies.Condensed,
        fontWeight = FontWeight.Bold,
    ),
    collectionTitleDark = PolygonTypeScale.titleLarge.copy(
        color = PolygonPalette.PrimaryText,
        fontFamily = PolygonFontFamilies.Condensed,
        fontWeight = FontWeight.Bold,
    ),
    collectionTitleLarge = PolygonTypeScale.headlineMedium.copy(
        color = PolygonPalette.PrimaryTextLight,
        fontFamily = PolygonFontFamilies.Mono,
        fontWeight = FontWeight.Bold,
    ),
    fileName = PolygonTypeScale.headlineMedium.copy(
        color = PolygonPalette.SecondaryText,
        fontFamily = PolygonFontFamilies.Condensed,
        fontWeight = FontWeight.Bold,
    ),
    fileSize = PolygonTypeScale.bodyMedium.copy(
        color = PolygonPalette.SecondaryText,
        fontFeatureSettings = "smcp, onum",
    ),
    tab = PolygonTypeScale.titleLarge.copy(
        fontWeight = FontWeight.Black,
        fontSize = 18.sp,
    ),
    downloadStatus = PolygonTypeScale.labelSmall.copy(
        color = PolygonPalette.PrimaryTextLight,
        fontWeight = FontWeight.Black,
        fontSize = 12.sp,
        letterSpacing = 0.15.em,
    ),
    downloadTimestamp = PolygonTypeScale.labelSmall.copy(
        color = PolygonPalette.DisabledHintText,
        fontWeight = FontWeight.Medium,
        fontFeatureSettings = "smcp, onum",
    ),
    searchQuery = PolygonTypeScale.titleLarge.copy(
        color = PolygonPalette.PrimaryText,
        fontWeight = FontWeight.Black,
    ),
    photoStatsChange = PolygonTypeScale.displayMedium.copy(
        color = PolygonPalette.PrimaryText,
        fontFamily = PolygonFontFamilies.Condensed,
    ),
    photoStatsChangeLight = PolygonTypeScale.displayMedium.copy(
        color = PolygonPalette.PrimaryTextLight,
        fontFamily = PolygonFontFamilies.Condensed,
    ),
    photoStatsChangeText = PolygonTypeScale.headlineMedium.copy(
        color = PolygonPalette.SecondaryText,
        fontFamily = PolygonFontFamilies.Condensed,
    ),
    photoStatsChangeTextLight = PolygonTypeScale.headlineMedium.copy(
        color = PolygonPalette.SecondaryTextLight,
        fontFamily = PolygonFontFamilies.Condensed,
    ),
    photoStatsLimits = PolygonTypeScale.labelSmall.copy(
        color = PolygonPalette.SecondaryText,
        fontWeight = FontWeight.Black,
        fontFeatureSettings = "smcp, onum",
        fontSize = 14.sp,
        letterSpacing = 0.02.em,
    ),
    photoStatsLimitsLight = PolygonTypeScale.labelSmall.copy(
        color = PolygonPalette.SecondaryTextLight,
        fontWeight = FontWeight.Black,
        fontFeatureSettings = "smcp, onum",
        fontSize = 14.sp,
        letterSpacing = 0.02.em,
    ),
    favDate = PolygonTypeScale.headlineLarge.copy(
        color = PolygonPalette.PrimaryText,
        fontFamily = PolygonFontFamilies.Condensed,
        fontWeight = FontWeight.Bold,
    ),
)

val LocalPolygonTypography = staticCompositionLocalOf { PolygonDefaultTypography }
