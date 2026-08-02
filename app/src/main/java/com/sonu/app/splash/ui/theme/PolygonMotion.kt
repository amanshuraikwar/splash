package com.sonu.app.splash.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class PolygonMotion(
    val fastDurationMillis: Int = 150,
    val standardDurationMillis: Int = 250,
    val slowDurationMillis: Int = 400,
    val dragDismissThreshold: Dp = 96.dp,
)

val PolygonDefaultMotion = PolygonMotion()

val LocalPolygonMotion = staticCompositionLocalOf { PolygonDefaultMotion }
