package com.raka.newslisttest.domain

import com.raka.newslisttest.data.model.remote.ArticleResponse
import io.reactivex.Single

interface NewsSourceRepository {
    fun loadNewsSource(category: String,page:String): Single<ArticleResponse>
}