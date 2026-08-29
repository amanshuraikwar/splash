package com.sonu.app.splash.data.media.unsplash

import com.sonu.app.splash.data.media.model.Creator
import com.sonu.app.splash.data.media.model.CreatorBadge
import com.sonu.app.splash.data.media.model.CreatorLinks
import com.sonu.app.splash.data.media.model.Media
import com.sonu.app.splash.data.media.model.MediaExif
import com.sonu.app.splash.data.media.model.MediaLinks
import com.sonu.app.splash.data.media.model.MediaLocation
import com.sonu.app.splash.data.media.model.MediaStats
import com.sonu.app.splash.data.media.model.MediaUrls
import com.sonu.app.splash.data.media.model.ProfileImage
import com.sonu.app.splash.data.media.model.StatsValues

object UnsplashMediaMapper {
    fun toDomain(dto: UnsplashMediaDto): Media = Media(
        id = dto.id,
        createdAt = dto.createdAt,
        updatedAt = dto.updatedAt,
        width = dto.width,
        height = dto.height,
        color = dto.color,
        description = dto.description ?: "- media on Unsplash",
        urls = dto.urls?.let(::toDomain),
        links = dto.links?.let(::toDomain),
        likes = dto.likes,
        creator = dto.creator?.let(::toDomain),
        location = dto.location?.let(::toDomain),
        exif = dto.exif?.let(::toDomain),
        views = dto.views,
        downloads = dto.downloads,
    )

    fun toDomain(dto: UnsplashCreatorDto): Creator = Creator(
        id = dto.id,
        updatedAt = dto.updatedAt,
        username = dto.username,
        name = dto.name,
        firstName = dto.firstName,
        lastName = dto.lastName,
        twitterUsername = dto.twitterUsername,
        portfolioUrl = dto.portfolioUrl,
        bio = dto.bio,
        location = dto.location,
        totalLikes = dto.totalLikes,
        totalMedia = dto.totalMedia,
        totalCollections = dto.totalCollections,
        followingCount = dto.followingCount,
        followersCount = dto.followersCount,
        downloads = dto.downloads,
        profileImage = dto.profileImage?.let(::toDomain),
        badge = dto.badge?.let {
            CreatorBadge(
                title = it.title,
                primary = it.primary,
                slug = it.slug,
                link = it.link,
            )
        },
        customTags = dto.tags?.custom.orEmpty().mapNotNull { it.title },
        aggregatedTags = dto.tags?.aggregated.orEmpty().mapNotNull { it.title },
        links = dto.links?.let {
            CreatorLinks(
                self = it.self,
                html = it.html,
                media = it.media,
                likes = it.likes,
                portfolio = it.portfolio,
                following = it.following,
                followers = it.followers,
            )
        },
    )

    fun toDomain(dto: UnsplashMediaStatsDto): MediaStats = MediaStats(
        id = dto.id,
        downloads = dto.downloads?.let(::toDomain) ?: StatsValues(),
        views = dto.views?.let(::toDomain) ?: StatsValues(),
        likes = dto.likes?.let(::toDomain) ?: StatsValues(),
    )

    fun toDomain(dto: UnsplashMediaUrlsDto) = MediaUrls(
        raw = dto.raw,
        full = dto.full,
        regular = dto.regular,
        small = dto.small,
        thumb = dto.thumb,
    )

    private fun toDomain(dto: UnsplashMediaLinksDto) = MediaLinks(
        self = dto.self,
        html = dto.html,
        download = dto.download,
        downloadLocation = dto.downloadLocation,
    )

    private fun toDomain(dto: UnsplashLocationDto) = MediaLocation(
        title = dto.title,
        name = dto.name,
        city = dto.city,
        country = dto.country,
        latitude = dto.position?.latitude,
        longitude = dto.position?.longitude,
    )

    private fun toDomain(dto: UnsplashExifDto) = MediaExif(
        make = dto.make,
        model = dto.model,
        exposureTime = dto.exposureTime,
        aperture = dto.aperture,
        focalLength = dto.focalLength,
        iso = dto.iso,
    )

    private fun toDomain(dto: UnsplashProfileImageDto) = ProfileImage(
        small = dto.small,
        medium = dto.medium,
        large = dto.large,
    )

    private fun toDomain(dto: UnsplashStatsValuesDto) = StatsValues(
        total = dto.total,
        change = dto.change,
        resolution = dto.resolution,
        quantity = dto.quantity,
        values = dto.values,
    )
}
