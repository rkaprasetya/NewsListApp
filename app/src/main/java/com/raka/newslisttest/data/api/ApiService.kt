package com.raka.newslisttest.data.api

import com.raka.newslisttest.data.model.remote.ArticleResponse
import com.raka.newslisttest.data.model.remote.NewsSourceResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("everything?")
    fun getSources(
        @Query("q")category: String,
        @Query("pageSize")pageSize:String,
        @Query("page")page:String,
        @Query("apiKey")apiKey:String
    ):Single<ArticleResponse>
    @GET("everything?")
    fun getArticles(
        @Query("q")category: String,
        @Query("sources")sources:String?,
        @Query("pageSize")pageSize:String,
        @Query("page")page:String,
        @Query("apiKey")apiKey:String
    ):Single<ArticleResponse>
}