package com.sonu.app.splash.data.media.unsplash

import com.google.gson.annotations.SerializedName

data class UnsplashSearchResponseDto(
    val results: List<UnsplashMediaDto> = emptyList(),
)

data class UnsplashCollectionSearchResponseDto(
    val results: List<UnsplashCollectionDto> = emptyList(),
)

data class UnsplashCreatorSearchResponseDto(
    val results: List<UnsplashCreatorDto> = emptyList(),
)

data class UnsplashMediaDto(
    val id: String,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null,
    val width: Int = 0,
    val height: Int = 0,
    val color: String? = null,
    val description: String? = null,
    val urls: UnsplashMediaUrlsDto? = null,
    val links: UnsplashMediaLinksDto? = null,
    val likes: Int = 0,
    @SerializedName("user") val creator: UnsplashCreatorDto? = null,
    val location: UnsplashLocationDto? = null,
    val exif: UnsplashExifDto? = null,
    val views: Int = 0,
    val downloads: Int = 0,
)

data class UnsplashMediaUrlsDto(
    val raw: String? = null,
    val full: String? = null,
    val regular: String? = null,
    val small: String? = null,
    val thumb: String? = null,
)

data class UnsplashMediaLinksDto(
    val self: String? = null,
    val html: String? = null,
    val download: String? = null,
    @SerializedName("download_location") val downloadLocation: String? = null,
)

data class UnsplashLocationDto(
    val title: String? = null,
    val name: String? = null,
    val city: String? = null,
    val country: String? = null,
    val position: UnsplashPositionDto? = null,
)

data class UnsplashPositionDto(
    val latitude: Double? = null,
    val longitude: Double? = null,
)

data class UnsplashExifDto(
    val make: String? = null,
    val model: String? = null,
    @SerializedName("exposure_time") val exposureTime: String? = null,
    val aperture: String? = null,
    @SerializedName("focal_length") val focalLength: String? = null,
    val iso: Int? = null,
)

data class UnsplashCreatorDto(
    val id: String,
    @SerializedName("updated_at") val updatedAt: String? = null,
    val username: String? = null,
    val name: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("twitter_username") val twitterUsername: String? = null,
    @SerializedName("portfolio_url") val portfolioUrl: String? = null,
    val bio: String? = null,
    val location: String? = null,
    @SerializedName("total_likes") val totalLikes: Int = 0,
    @SerializedName("total_photos") val totalMedia: Int = 0,
    @SerializedName("total_collections") val totalCollections: Int = 0,
    @SerializedName("following_count") val followingCount: Int = 0,
    @SerializedName("followers_count") val followersCount: Int = 0,
    val downloads: Int = 0,
    @SerializedName("profile_image") val profileImage: UnsplashProfileImageDto? = null,
    val badge: UnsplashCreatorBadgeDto? = null,
    val tags: UnsplashCreatorTagsDto? = null,
    val links: UnsplashCreatorLinksDto? = null,
)

data class UnsplashCollectionDto(
    val id: String,
    val title: String? = null,
    val description: String? = null,
    @SerializedName("published_at") val publishedAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null,
    val curated: Boolean = false,
    val featured: Boolean = false,
    @SerializedName("total_photos") val totalMedia: Int = 0,
    @SerializedName("private") val isPrivate: Boolean = false,
    @SerializedName("share_key") val shareKey: String? = null,
    val tags: List<UnsplashTagDto> = emptyList(),
    @SerializedName("cover_photo") val coverMedia: UnsplashMediaDto? = null,
    @SerializedName("preview_photos") val previewMedia: List<UnsplashPreviewMediaDto> = emptyList(),
    val user: UnsplashCreatorDto? = null,
    val links: UnsplashCollectionLinksDto? = null,
)

data class UnsplashTagDto(
    val title: String? = null,
)

data class UnsplashPreviewMediaDto(
    val id: String,
    val urls: UnsplashMediaUrlsDto? = null,
)

data class UnsplashCollectionLinksDto(
    val self: String? = null,
    val html: String? = null,
    @SerializedName("photos") val media: String? = null,
    val related: String? = null,
)

data class UnsplashProfileImageDto(
    val small: String? = null,
    val medium: String? = null,
    val large: String? = null,
)

data class UnsplashCreatorBadgeDto(
    val title: String? = null,
    val primary: Boolean = false,
    val slug: String? = null,
    val link: String? = null,
)

data class UnsplashCreatorTagsDto(
    val custom: List<UnsplashTagDto> = emptyList(),
    val aggregated: List<UnsplashTagDto> = emptyList(),
)

data class UnsplashCreatorLinksDto(
    val self: String? = null,
    val html: String? = null,
    @SerializedName("photos") val media: String? = null,
    val likes: String? = null,
    val portfolio: String? = null,
    val following: String? = null,
    val followers: String? = null,
)

data class UnsplashMediaStatsDto(
    val id: String,
    val downloads: UnsplashStatsValuesDto? = null,
    val views: UnsplashStatsValuesDto? = null,
    val likes: UnsplashStatsValuesDto? = null,
)

data class UnsplashStatsValuesDto(
    val total: Int = 0,
    val change: Int = 0,
    val resolution: String? = null,
    val quantity: Int = 0,
    val values: Map<String, Int> = emptyMap(),
)
