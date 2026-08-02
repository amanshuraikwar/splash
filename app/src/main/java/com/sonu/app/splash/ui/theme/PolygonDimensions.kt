package com.sonu.app.splash.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class PolygonDimensions(
    val screenMarginHorizontal: Dp = 16.dp,
    val screenMarginVertical: Dp = 16.dp,
    val downloadSheetHeight: Dp = 104.dp,
    val persistentMessageHeight: Dp = 26.dp,
    val largeAvatarSize: Dp = 120.dp,
    val avatarSize: Dp = 40.dp,
    val userPictureSmall: Dp = 72.dp,
    val iconButtonTouchTarget: Dp = 48.dp,
    val inlineProgressWidth: Dp = 56.dp,
    val inlineProgressHeight: Dp = 4.dp,
)

val PolygonDefaultDimensions = PolygonDimensions()

val LocalPolygonDimensions = staticCompositionLocalOf { PolygonDefaultDimensions }
