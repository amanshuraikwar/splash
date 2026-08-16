package com.sonu.app.polygon.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Immutable
data class PolygonShapes(
    val none: Shape = RoundedCornerShape(0.dp),
    val extraSmall: Shape = RoundedCornerShape(2.dp),
    val small: Shape = RoundedCornerShape(4.dp),
    val medium: Shape = RoundedCornerShape(8.dp),
    val large: Shape = RoundedCornerShape(12.dp),
    val avatar: Shape = RoundedCornerShape(percent = 50),
)

val PolygonDefaultShapes = PolygonShapes()

val LocalPolygonShapes = staticCompositionLocalOf { PolygonDefaultShapes }
