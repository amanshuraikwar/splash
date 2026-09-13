package com.sonu.app.splash.data.media.model

data class MediaStats(
    val id: String,
    val downloads: StatsValues = StatsValues(),
    val views: StatsValues = StatsValues(),
    val likes: StatsValues = StatsValues(),
)

data class StatsValues(
    val total: Int = 0,
    val change: Int = 0,
    val resolution: String? = null,
    val quantity: Int = 0,
    val values: Map<String, Int> = emptyMap(),
)
