package com.raka.newslisttest.domain.mapper

import com.raka.newslisttest.data.model.compact.ArticleCompact
import com.raka.newslisttest.data.model.remote.ArticlesItem

class ArticleMapper {
    fun convertArticleRemoteToCompact(data: List<ArticlesItem?>?):List<ArticleCompact>{
        val listCompact:MutableList<ArticleCompact> = mutableListOf()
        data!!.forEach { item->
            listCompact.add(ArticleCompact(item?.publishedAt,
                                        item?.urlToImage,
                                        item?.description,
                                        item?.title,
                                        item?.url))
        }
        return listCompact
    }
}