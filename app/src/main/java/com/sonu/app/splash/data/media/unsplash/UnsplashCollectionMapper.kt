package com.sonu.app.splash.data.media.unsplash

import com.sonu.app.splash.data.media.model.MediaCollection
import com.sonu.app.splash.data.media.model.MediaCollectionLinks
import com.sonu.app.splash.data.media.model.MediaPreview

object UnsplashCollectionMapper {
    fun toDomain(dto: UnsplashCollectionDto): MediaCollection = MediaCollection(
        id = dto.id,
        title = dto.title,
        description = dto.description,
        publishedAt = dto.publishedAt,
        updatedAt = dto.updatedAt,
        curated = dto.curated,
        featured = dto.featured,
        totalMedia = dto.totalMedia,
        isPrivate = dto.isPrivate,
        shareKey = dto.shareKey,
        tags = dto.tags.mapNotNull { it.title },
        coverMedia = dto.coverMedia?.let(UnsplashMediaMapper::toDomain),
        previewMedia = dto.previewMedia.map { preview ->
            MediaPreview(
                id = preview.id,
                urls = preview.urls?.let { UnsplashMediaMapper.toDomain(it) },
            )
        },
        creator = dto.user?.let(UnsplashMediaMapper::toDomain),
        links = dto.links?.let {
            MediaCollectionLinks(
                self = it.self,
                html = it.html,
                media = it.media,
                related = it.related,
            )
        },
    )
}
