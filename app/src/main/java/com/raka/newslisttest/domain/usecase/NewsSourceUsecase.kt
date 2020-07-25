package com.raka.newslisttest.domain.usecase

import com.raka.newslisttest.data.model.compact.NewsSourceCompact
import com.raka.newslisttest.domain.NewsSourceRepository
import com.raka.newslisttest.domain.mapper.NewsSourceMapper
import io.reactivex.Single
import javax.inject.Inject

class NewsSourceUsecase @Inject constructor(private val repository: NewsSourceRepository) {
    private val mapper= NewsSourceMapper()
    fun fetchNewsSources(category:String,page:String): Single<List<NewsSourceCompact>> {
        return repository.loadNewsSource(category,page).map { mapper.convertRemoteToCompact(it.articles) }
    }
}