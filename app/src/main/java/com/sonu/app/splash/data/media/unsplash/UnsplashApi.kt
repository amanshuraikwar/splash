package com.sonu.app.splash.data.media.unsplash

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {
    @GET("photos")
    suspend fun getAllMedia(
        @Query("page") page: Int,
        @Query("order_by") order: String,
        @Query("per_page") perPage: Int,
    ): List<UnsplashMediaDto>

    @GET("photos")
    suspend fun getCuratedMedia(
        @Query("page") page: Int,
        @Query("order_by") order: String,
        @Query("per_page") perPage: Int,
    ): List<UnsplashMediaDto>

    @GET("search/photos")
    suspend fun searchMedia(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): UnsplashSearchResponseDto

    @GET("photos/{id}")
    suspend fun getMedia(@Path("id") id: String): UnsplashMediaDto

    @GET("photos/{id}/statistics")
    suspend fun getMediaStatistics(
        @Path("id") id: String,
        @Query("resolution") resolution: String = "days",
        @Query("quantity") quantity: Int = 30,
    ): UnsplashMediaStatsDto

    @GET("users/{username}")
    suspend fun getCreator(@Path("username") username: String): UnsplashCreatorDto

    @GET("users/{username}/photos")
    suspend fun getCreatorMedia(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("order_by") order: String,
        @Query("per_page") perPage: Int,
    ): List<UnsplashMediaDto>

    @GET("collections/{id}/photos")
    suspend fun getCollectionMedia(
        @Path("id") id: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<UnsplashMediaDto>

    @GET("collections")
    suspend fun getCollections(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<UnsplashCollectionDto>

    @GET("collections/featured")
    suspend fun getFeaturedCollections(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<UnsplashCollectionDto>

    @GET("search/collections")
    suspend fun searchCollections(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): UnsplashCollectionSearchResponseDto

    @GET("users/{username}/collections")
    suspend fun getCreatorCollections(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<UnsplashCollectionDto>

    @GET("search/users")
    suspend fun searchCreators(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): UnsplashCreatorSearchResponseDto
}
