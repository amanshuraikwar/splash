package com.sonu.app.splash.data.media.model

data class Media(
    val id: String,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val width: Int = 0,
    val height: Int = 0,
    val color: String? = null,
    val description: String? = null,
    val urls: MediaUrls? = null,
    val links: MediaLinks? = null,
    val likes: Int = 0,
    val creator: Creator? = null,
    val location: MediaLocation? = null,
    val exif: MediaExif? = null,
    val views: Int = 0,
    val downloads: Int = 0,
)

data class MediaUrls(
    val raw: String? = null,
    val full: String? = null,
    val regular: String? = null,
    val small: String? = null,
    val thumb: String? = null,
)

data class MediaLinks(
    val self: String? = null,
    val html: String? = null,
    val download: String? = null,
    val downloadLocation: String? = null,
)

data class MediaLocation(
    val title: String? = null,
    val name: String? = null,
    val city: String? = null,
    val country: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
)

data class MediaExif(
    val make: String? = null,
    val model: String? = null,
    val exposureTime: String? = null,
    val aperture: String? = null,
    val focalLength: String? = null,
    val iso: Int? = null,
)
