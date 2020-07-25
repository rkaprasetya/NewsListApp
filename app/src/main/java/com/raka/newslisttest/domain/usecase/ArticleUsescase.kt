package com.raka.newslisttest.domain.usecase

import com.raka.newslisttest.data.model.compact.ArticleCompact
import com.raka.newslisttest.domain.ArticleRepository
import com.raka.newslisttest.domain.mapper.ArticleMapper
import io.reactivex.Single
import javax.inject.Inject

class ArticleUsescase @Inject constructor(private val repository: ArticleRepository) {
    private val mapper =ArticleMapper()
    fun fetchArticles(category:String,source:String,page:String): Single<List<ArticleCompact>> {
        return repository.loadArticles(category,source, page).map { mapper.convertArticleRemoteToCompact(it.articles) }
    }
}