package com.raka.newslisttest.domain

import com.raka.newslisttest.data.model.remote.ArticleResponse
import io.reactivex.Single

interface ArticleRepository {
    fun loadArticles(category: String,source: String?,page:String): Single<ArticleResponse>
}