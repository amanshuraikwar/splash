package com.sonu.app.splash.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

object SplashFontFamilies {
    val Sans = FontFamily.SansSerif
    val Mono = FontFamily.Monospace
    val Condensed = FontFamily.SansSerif
}

@Immutable
data class SplashTextStyles(
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
    val favDate: TextStyle
)

val SplashMaterialTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = SplashFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 112.sp
    ),
    displayMedium = TextStyle(
        fontFamily = SplashFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 56.sp
    ),
    displaySmall = TextStyle(
        fontFamily = SplashFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = SplashFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = SplashFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = SplashFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = SplashFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = SplashFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = SplashFontFamilies.Sans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = SplashFontFamilies.Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)

val SplashDefaultTextStyles = SplashTextStyles(
    toolbarTitleSmallCaps = SplashMaterialTypography.titleLarge.copy(
        color = SplashPalette.PrimaryText,
        fontFeatureSettings = "smcp",
        letterSpacing = 0.2.em
    ),
    toolbarTitleBlack = SplashMaterialTypography.titleLarge.copy(
        color = SplashPalette.PrimaryText,
        fontWeight = FontWeight.Black,
        fontSize = 18.sp
    ),
    persistentMessage = SplashMaterialTypography.labelSmall.copy(
        color = SplashPalette.PrimaryTextLight,
        fontWeight = FontWeight.Black
    ),
    appNameBig = SplashMaterialTypography.displayMedium.copy(
        color = SplashPalette.SecondaryText,
        fontWeight = FontWeight.Medium,
        fontFeatureSettings = "smcp",
        letterSpacing = 0.2.em
    ),
    appShortDescription = SplashMaterialTypography.labelSmall.copy(
        color = SplashPalette.DisabledHintText,
        fontWeight = FontWeight.Medium,
        fontFeatureSettings = "smcp"
    ),
    appInfoHeading = SplashMaterialTypography.bodyMedium.copy(
        color = SplashPalette.DisabledHintText,
        fontFamily = SplashFontFamilies.Condensed,
        fontWeight = FontWeight.Bold
    ),
    appInfoElementTitle = SplashMaterialTypography.titleMedium.copy(
        color = SplashPalette.PrimaryText,
        fontWeight = FontWeight.Medium
    ),
    appInfoElementSubtitle = SplashMaterialTypography.bodyLarge.copy(
        color = SplashPalette.SecondaryText
    ),
    locationTitle = SplashMaterialTypography.bodyMedium.copy(
        color = SplashPalette.SecondaryText,
        fontFamily = SplashFontFamilies.Condensed,
        fontWeight = FontWeight.Bold
    ),
    photoDescription = SplashMaterialTypography.headlineMedium.copy(
        color = SplashPalette.PrimaryText,
        fontFamily = SplashFontFamilies.Mono
    ),
    photoStatsValue = SplashMaterialTypography.labelSmall.copy(
        color = SplashPalette.SecondaryText,
        fontWeight = FontWeight.Medium,
        fontFeatureSettings = "smcp, onum",
        fontSize = 14.sp,
        letterSpacing = 0.02.em
    ),
    artistName = SplashMaterialTypography.bodyLarge.copy(
        color = SplashPalette.PrimaryText,
        fontWeight = FontWeight.Medium,
        fontFeatureSettings = "smcp, onum",
        letterSpacing = 0.02.em
    ),
    artistNameLight = SplashMaterialTypography.bodyLarge.copy(
        color = SplashPalette.PrimaryTextLight,
        fontWeight = FontWeight.Medium,
        fontFeatureSettings = "smcp, onum",
        letterSpacing = 0.02.em
    ),
    artistUsername = SplashMaterialTypography.labelSmall.copy(
        color = SplashPalette.DisabledHintText,
        fontFamily = SplashFontFamilies.Mono,
        fontWeight = FontWeight.Medium
    ),
    artistNameBig = SplashMaterialTypography.titleLarge.copy(
        color = SplashPalette.PrimaryText,
        fontWeight = FontWeight.Medium,
        fontFeatureSettings = "smcp, onum",
        fontSize = 20.sp,
        letterSpacing = 0.02.em
    ),
    artistUsernameBig = SplashMaterialTypography.bodyLarge.copy(
        color = SplashPalette.DisabledHintText,
        fontFamily = SplashFontFamilies.Mono,
        fontWeight = FontWeight.Medium
    ),
    artistBio = SplashMaterialTypography.titleMedium.copy(
        color = SplashPalette.SecondaryText,
        fontFamily = SplashFontFamilies.Mono
    ),
    errorTitle = SplashMaterialTypography.titleLarge.copy(
        color = SplashPalette.PrimaryText,
        fontWeight = FontWeight.Bold,
        fontFeatureSettings = "smcp"
    ),
    errorTitleLight = SplashMaterialTypography.titleLarge.copy(
        color = SplashPalette.PrimaryTextLight,
        fontWeight = FontWeight.Bold,
        fontFeatureSettings = "smcp"
    ),
    errorMessage = SplashMaterialTypography.bodyMedium.copy(
        color = SplashPalette.SecondaryText
    ),
    errorMessageLight = SplashMaterialTypography.bodyMedium.copy(
        color = SplashPalette.SecondaryTextLight
    ),
    button = SplashMaterialTypography.labelLarge.copy(
        color = SplashPalette.PrimaryText,
        fontFamily = SplashFontFamilies.Mono,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.1.em
    ),
    buttonSmall = SplashMaterialTypography.labelLarge.copy(
        color = SplashPalette.PrimaryText,
        fontFamily = SplashFontFamilies.Mono,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        letterSpacing = 0.1.em
    ),
    buttonLight = SplashMaterialTypography.labelLarge.copy(
        color = SplashPalette.PrimaryTextLight,
        fontFamily = SplashFontFamilies.Mono,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.1.em
    ),
    artistTag = SplashMaterialTypography.bodyMedium.copy(
        color = SplashPalette.DisabledHintText,
        fontFamily = SplashFontFamilies.Condensed
    ),
    filter = SplashMaterialTypography.titleMedium.copy(
        color = SplashPalette.SecondaryText,
        fontWeight = FontWeight.Medium
    ),
    header = SplashMaterialTypography.bodyMedium.copy(
        color = SplashPalette.DisabledHintText,
        fontFamily = SplashFontFamilies.Condensed,
        fontWeight = FontWeight.Bold
    ),
    largeHeader = SplashMaterialTypography.titleLarge.copy(
        color = SplashPalette.DisabledHintText,
        fontFamily = SplashFontFamilies.Condensed,
        fontWeight = FontWeight.Bold
    ),
    overlayLarge = SplashMaterialTypography.headlineLarge.copy(
        color = SplashPalette.SecondaryText,
        fontFamily = SplashFontFamilies.Condensed,
        fontWeight = FontWeight.Bold
    ),
    collectionPhotoCount = SplashMaterialTypography.bodyLarge.copy(
        color = SplashPalette.SecondaryTextLight,
        fontWeight = FontWeight.Bold,
        fontFeatureSettings = "smcp, onum"
    ),
    collectionTitle = SplashMaterialTypography.titleLarge.copy(
        color = SplashPalette.PrimaryTextLight,
        fontFamily = SplashFontFamilies.Condensed,
        fontWeight = FontWeight.Bold
    ),
    collectionTitleDark = SplashMaterialTypography.titleLarge.copy(
        color = SplashPalette.PrimaryText,
        fontFamily = SplashFontFamilies.Condensed,
        fontWeight = FontWeight.Bold
    ),
    collectionTitleLarge = SplashMaterialTypography.headlineMedium.copy(
        color = SplashPalette.PrimaryTextLight,
        fontFamily = SplashFontFamilies.Mono,
        fontWeight = FontWeight.Bold
    ),
    fileName = SplashMaterialTypography.headlineMedium.copy(
        color = SplashPalette.SecondaryText,
        fontFamily = SplashFontFamilies.Condensed,
        fontWeight = FontWeight.Bold
    ),
    fileSize = SplashMaterialTypography.bodyMedium.copy(
        color = SplashPalette.SecondaryText,
        fontFeatureSettings = "smcp, onum"
    ),
    tab = SplashMaterialTypography.titleLarge.copy(
        fontWeight = FontWeight.Black,
        fontSize = 18.sp
    ),
    downloadStatus = SplashMaterialTypography.labelSmall.copy(
        color = SplashPalette.PrimaryTextLight,
        fontWeight = FontWeight.Black,
        fontSize = 12.sp,
        letterSpacing = 0.15.em
    ),
    downloadTimestamp = SplashMaterialTypography.labelSmall.copy(
        color = SplashPalette.DisabledHintText,
        fontWeight = FontWeight.Medium,
        fontFeatureSettings = "smcp, onum"
    ),
    searchQuery = SplashMaterialTypography.titleLarge.copy(
        color = SplashPalette.PrimaryText,
        fontWeight = FontWeight.Black
    ),
    photoStatsChange = SplashMaterialTypography.displayMedium.copy(
        color = SplashPalette.PrimaryText,
        fontFamily = SplashFontFamilies.Condensed
    ),
    photoStatsChangeLight = SplashMaterialTypography.displayMedium.copy(
        color = SplashPalette.PrimaryTextLight,
        fontFamily = SplashFontFamilies.Condensed
    ),
    photoStatsChangeText = SplashMaterialTypography.headlineMedium.copy(
        color = SplashPalette.SecondaryText,
        fontFamily = SplashFontFamilies.Condensed
    ),
    photoStatsChangeTextLight = SplashMaterialTypography.headlineMedium.copy(
        color = SplashPalette.SecondaryTextLight,
        fontFamily = SplashFontFamilies.Condensed
    ),
    photoStatsLimits = SplashMaterialTypography.labelSmall.copy(
        color = SplashPalette.SecondaryText,
        fontWeight = FontWeight.Black,
        fontFeatureSettings = "smcp, onum",
        fontSize = 14.sp,
        letterSpacing = 0.02.em
    ),
    photoStatsLimitsLight = SplashMaterialTypography.labelSmall.copy(
        color = SplashPalette.SecondaryTextLight,
        fontWeight = FontWeight.Black,
        fontFeatureSettings = "smcp, onum",
        fontSize = 14.sp,
        letterSpacing = 0.02.em
    ),
    favDate = SplashMaterialTypography.headlineLarge.copy(
        color = SplashPalette.PrimaryText,
        fontFamily = SplashFontFamilies.Condensed,
        fontWeight = FontWeight.Bold
    )
)

val LocalSplashTextStyles = staticCompositionLocalOf { SplashDefaultTextStyles }
