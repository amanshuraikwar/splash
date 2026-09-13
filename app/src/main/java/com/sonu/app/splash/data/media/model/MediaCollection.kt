package com.sonu.app.splash.data.media.model

data class MediaCollection(
    val id: String,
    val title: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val updatedAt: String? = null,
    val curated: Boolean = false,
    val featured: Boolean = false,
    val totalMedia: Int = 0,
    val isPrivate: Boolean = false,
    val shareKey: String? = null,
    val tags: List<String> = emptyList(),
    val coverMedia: Media? = null,
    val previewMedia: List<MediaPreview> = emptyList(),
    val creator: Creator? = null,
    val links: MediaCollectionLinks? = null,
)

data class MediaPreview(
    val id: String,
    val urls: MediaUrls? = null,
)

data class MediaCollectionLinks(
    val self: String? = null,
    val html: String? = null,
    val media: String? = null,
    val related: String? = null,
)
