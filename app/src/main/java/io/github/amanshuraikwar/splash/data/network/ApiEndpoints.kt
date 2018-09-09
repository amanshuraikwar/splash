package io.github.amanshuraikwar.splash.data.network


/**
 * API endpoints for the remote unsplash.com api.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 30/04/18.
 */

@Suppress("MemberVisibilityCanBePrivate")
interface ApiEndpoints {

    companion object {

        const val PER_PAGE = 30
        const val STATS_QUANTITY = 30
        const val STATS_RESOLUTION = "days"

        const val BASE_ENDPOINT = "https://api.unsplash.com/"

        const val GET_ALL_PHOTOS = "photos?"

        const val GET_CURATED_PHOTOS = "photos/curated?"

        const val GET_PHOTO = "photos/{id}"

        const val SEARCH_PHOTOS = "search/photos?"

        const val GET_USER = "users/{id}"

        const val GET_USER_PHOTOS = "users/{id}/photos?"

        const val GET_USER_COLLECTIONS = "users/{id}/collections?"

        const val SEARCH_USERS = "search/users?"

        const val GET_ALL_COLLECTIONS = "collections?"

        const val GET_FEATURED_COLLECTIONS = "collections/featured?"

        const val GET_CURATED_COLLECTIONS = "collections/curated?"

        const val SEARCH_COLLECTIONS = "search/collections?"

        const val GET_COLLECTION= "collections/{id}"

        const val GET_COLLECTION_PHOTOS = "collections/{id}/photos?"

        const val GET_PHOTO_STATISTICS = "photos/{id}/statistics?"
    }
}