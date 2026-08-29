package com.sonu.app.splash.data.rss.github

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface RssApi {
    @GET(".")
    suspend fun getIndex(): ResponseBody

    @GET
    suspend fun getFeed(@Url url: String): ResponseBody
}
