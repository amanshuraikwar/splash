package com.sonu.app.splash.ui.legacy

import com.sonu.app.splash.data.media.model.Creator
import com.sonu.app.splash.data.media.model.Media
import com.sonu.app.splash.data.media.model.MediaCollection
import com.sonu.app.splash.data.media.model.MediaCollectionLinks
import com.sonu.app.splash.data.media.model.MediaStats
import com.sonu.app.splash.data.media.model.MediaUrls
import com.sonu.app.splash.data.media.model.MediaPreview
import com.sonu.app.splash.data.media.model.StatsValues
import com.sonu.app.splash.model.unsplash.Collection
import com.sonu.app.splash.model.unsplash.CollectionLinks
import com.sonu.app.splash.model.unsplash.CollectionPreviewPhoto
import com.sonu.app.splash.model.unsplash.Exif
import com.sonu.app.splash.model.unsplash.Location
import com.sonu.app.splash.model.unsplash.Photo
import com.sonu.app.splash.model.unsplash.PhotoLinks
import com.sonu.app.splash.model.unsplash.PhotoStats
import com.sonu.app.splash.model.unsplash.PhotoUrls
import com.sonu.app.splash.model.unsplash.ProfileImage
import com.sonu.app.splash.model.unsplash.StatsValues as LegacyStatsValues
import com.sonu.app.splash.model.unsplash.User
import com.sonu.app.splash.model.unsplash.UserLinks
import com.sonu.app.splash.model.unsplash.UserTags

/** Converts domain data into the existing UI-only Parcelable models. */
object LegacyUiModelMapper {
    @JvmStatic
    fun toPhoto(media: Media): Photo = Photo.Builder(media.id)
        .createdAt(media.createdAt)
        .updatedAt(media.updatedAt)
        .width(media.width)
        .height(media.height)
        .color(media.color)
        .description(media.description)
        .urls(media.urls?.let(::toPhotoUrls))
        .links(media.links?.let(::toPhotoLinks))
        .likes(media.likes)
        .user(media.creator?.let(::toUser))
        .location(media.location?.let {
            Location.Builder()
                .title(it.title)
                .name(it.name)
                .city(it.city)
                .country(it.country)
                .lat(it.latitude ?: 0.0)
                .lon(it.longitude ?: 0.0)
                .build()
        })
        .exif(media.exif?.let {
            Exif.Builder()
                .make(it.make)
                .model(it.model)
                .exposureTime(it.exposureTime)
                .aperture(it.aperture)
                .focalLength(it.focalLength)
                .iso(it.iso ?: 0)
                .build()
        })
        .views(media.views)
        .downloads(media.downloads)
        .build()

    @JvmStatic
    fun toUser(creator: Creator): User {
        val builder = User.Builder(creator.id)
            .username(creator.username)
            .updatedAt(creator.updatedAt)
            .name(creator.name)
            .firstName(creator.firstName)
            .lastName(creator.lastName)
            .twitterUsername(creator.twitterUsername)
            .portfolioUrl(creator.portfolioUrl)
            .bio(creator.bio)
            .location(creator.location)
            .totalLikes(creator.totalLikes)
            .totalPhotos(creator.totalMedia)
            .totalCollections(creator.totalCollections)
            .followingCount(creator.followingCount)
            .followersCount(creator.followersCount)
            .downloads(creator.downloads)

        creator.profileImage?.let {
            builder.profileImage(
                ProfileImage.Builder()
                    .small(it.small)
                    .meduim(it.medium)
                    .large(it.large)
                    .build(),
            )
        }

        builder.badge(creator.badge?.let {
            com.sonu.app.splash.model.unsplash.Badge.Builder(it.title ?: "")
                .primary(it.primary)
                .slug(it.slug)
                .link(it.link)
                .build()
        })
        builder.tags(
            UserTags.Builder()
                .custom(creator.customTags.toTypedArray())
                .aggregated(creator.aggregatedTags.toTypedArray())
                .build(),
        )
        builder.userLinks(creator.links?.let {
            UserLinks.Builder()
                .self(it.self)
                .html(it.html)
                .photos(it.media)
                .likes(it.likes)
                .portfolio(it.portfolio)
                .following(it.following)
                .followers(it.followers)
                .build()
        } ?: UserLinks.Builder().build())
        return builder.build()
    }

    @JvmStatic
    fun toCollection(collection: MediaCollection): Collection {
        val builder = Collection.Builder(collection.id)
            .title(collection.title)
            .description(collection.description)
            .publishedAt(collection.publishedAt)
            .updatedAt(collection.updatedAt)
            .curated(collection.curated)
            .featured(collection.featured)
            .totalPhotos(collection.totalMedia)
            .privateC(collection.isPrivate)
            .shareKey(collection.shareKey)
            .tags(collection.tags.toTypedArray())
            .coverPhoto(collection.coverMedia?.let(::toPhoto))
            .user(collection.creator?.let(::toUser))

        collection.previewMedia
            .map(::toPreviewPhoto)
            .toTypedArray()
            .also(builder::previewPhotos)

        collection.links?.let { builder.collectionLinks(toCollectionLinks(it)) }
        return builder.build()
    }

    @JvmStatic
    fun toPhotoStats(stats: MediaStats): PhotoStats = PhotoStats.Builder(stats.id)
        .downloads(toLegacyStatsValues(stats.downloads))
        .views(toLegacyStatsValues(stats.views))
        .likes(toLegacyStatsValues(stats.likes))
        .build()

    private fun toPhotoUrls(urls: MediaUrls): PhotoUrls = PhotoUrls.Builder()
        .raw(urls.raw)
        .full(urls.full)
        .regular(urls.regular)
        .small(urls.small)
        .thumb(urls.thumb)
        .build()

    private fun toPhotoLinks(links: com.sonu.app.splash.data.media.model.MediaLinks): PhotoLinks =
        PhotoLinks.Builder()
            .self(links.self)
            .html(links.html)
            .download(links.download)
            .downloadLocation(links.downloadLocation)
            .build()

    private fun toPreviewPhoto(preview: MediaPreview): CollectionPreviewPhoto =
        CollectionPreviewPhoto.Builder(preview.id)
            .photoUrls(preview.urls?.let(::toPhotoUrls))
            .build()

    private fun toCollectionLinks(links: MediaCollectionLinks): CollectionLinks =
        CollectionLinks.Builder()
            .self(links.self)
            .html(links.html)
            .photos(links.media)
            .related(links.related)
            .build()

    @Suppress("UNCHECKED_CAST")
    private fun toLegacyStatsValues(values: StatsValues): LegacyStatsValues<Int> {
        val builder = LegacyStatsValues.Builder<Int>()
            .total(values.total)
            .change(values.change)
            .resolution(values.resolution)
            .quantity(values.quantity)

        values.values.forEach { (date, value) -> builder.value(date, value) }
        return builder.build() as LegacyStatsValues<Int>
    }
}
