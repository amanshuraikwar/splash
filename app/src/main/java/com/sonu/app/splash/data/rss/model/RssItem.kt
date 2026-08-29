package com.sonu.app.splash.data.rss.model

data class RssItem(
    val id: String,
    val feedTitle: String,
    val feedUrl: String,
    val title: String,
    val link: String?,
    val description: String?,
    val imageUrl: String?,
    val publishedAtEpochMillis: Long?,
)
