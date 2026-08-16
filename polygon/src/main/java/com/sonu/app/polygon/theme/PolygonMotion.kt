package com.sonu.app.polygon.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class PolygonMotion(
    val fastDurationMillis: Int = 195,
    val standardDurationMillis: Int = 300,
    val slowDurationMillis: Int = 375,
    val dragDismissThreshold: Dp = 96.dp,
)

val PolygonDefaultMotion = PolygonMotion()

val LocalPolygonMotion = staticCompositionLocalOf { PolygonDefaultMotion }
