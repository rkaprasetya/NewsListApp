package com.raka.newslisttest.data.repository

import com.raka.newslisttest.data.api.ApiService
import com.raka.newslisttest.data.model.remote.ArticleResponse
import com.raka.newslisttest.domain.NewsSourceRepository
import com.raka.newslisttest.utils.Constants
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsSourceRepositoryImpl @Inject constructor(private val apiService: ApiService):NewsSourceRepository {
    override fun loadNewsSource(category: String,page:String): Single<ArticleResponse> {
        return apiService.getSources(category,"30",page,Constants.API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}