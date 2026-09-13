package com.sonu.app.splash.data.media.model

data class Creator(
    val id: String,
    val updatedAt: String? = null,
    val username: String? = null,
    val name: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val twitterUsername: String? = null,
    val portfolioUrl: String? = null,
    val bio: String? = null,
    val location: String? = null,
    val totalLikes: Int = 0,
    val totalMedia: Int = 0,
    val totalCollections: Int = 0,
    val followingCount: Int = 0,
    val followersCount: Int = 0,
    val downloads: Int = 0,
    val profileImage: ProfileImage? = null,
    val badge: CreatorBadge? = null,
    val customTags: List<String> = emptyList(),
    val aggregatedTags: List<String> = emptyList(),
    val links: CreatorLinks? = null,
)

data class ProfileImage(
    val small: String? = null,
    val medium: String? = null,
    val large: String? = null,
)

data class CreatorBadge(
    val title: String? = null,
    val primary: Boolean = false,
    val slug: String? = null,
    val link: String? = null,
)

data class CreatorLinks(
    val self: String? = null,
    val html: String? = null,
    val media: String? = null,
    val likes: String? = null,
    val portfolio: String? = null,
    val following: String? = null,
    val followers: String? = null,
)
