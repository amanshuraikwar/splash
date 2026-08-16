package com.sonu.app.polygon.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class PolygonElevation(
    val none: Dp = 0.dp,
    val small: Dp = 2.dp,
    val medium: Dp = 4.dp,
    val large: Dp = 8.dp,
    val persistentMessage: Dp = 6.dp,
)

val PolygonDefaultElevation = PolygonElevation()

val LocalPolygonElevation = staticCompositionLocalOf { PolygonDefaultElevation }
