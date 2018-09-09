package io.github.amanshuraikwar.splash.data.network

import io.github.amanshuraikwar.splash.model.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Retrofit api interface.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 30/04/18.
 */
interface ApiInterface {

    @Suppress("unused")
    companion object {
        const val ORDER_BY_LATEST = "latest"
        const val ORDER_BY_OLDEST = "oldest"
        const val ORDER_BY_POPULAR = "popular"
        const val VERSION = "v1"
        const val ACCESS_KEY = "8b751ed9c3cb1e5122f9103d2fd028f82f714191f5877bf69908aa30ba73b7b6"
    }


    @Headers(
        value = ["Accept: application/json",
        "Accept-Version: $VERSION",
        "Authorization: Client-ID $ACCESS_KEY"])
    @GET(ApiEndpoints.GET_ALL_PHOTOS)
    fun getAllPhotos(@Query("page") page: Int,
                     @Query("order_by") orderBy: String,
                     @Query("per_page") perPage: Int = ApiEndpoints.PER_PAGE)
            : Call<List<Photo>>

    @Headers(
            value = ["Accept: application/json",
                "Accept-Version: $VERSION",
                "Authorization: Client-ID $ACCESS_KEY"])
    @GET(ApiEndpoints.GET_CURATED_PHOTOS)
    fun getCuratedPhotos(@Query("page") page: Int,
                     @Query("order_by") orderBy: String,
                     @Query("per_page") perPage: Int = ApiEndpoints.PER_PAGE)
            : Call<List<Photo>>
}