package com.raka.newslisttest.data.repository

import com.raka.newslisttest.data.api.ApiService
import com.raka.newslisttest.data.model.remote.ArticleResponse
import com.raka.newslisttest.domain.ArticleRepository
import com.raka.newslisttest.utils.Constants
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(private val apiService: ApiService):ArticleRepository {
    override fun loadArticles(
        category: String,
        source: String?,
        page: String
    ): Single<ArticleResponse> {
        return apiService.getArticles(category,source,"7",page, Constants.API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}