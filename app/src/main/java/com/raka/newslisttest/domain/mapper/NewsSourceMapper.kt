package com.raka.newslisttest.domain.mapper

import com.raka.newslisttest.data.model.compact.NewsSourceCompact
import com.raka.newslisttest.data.model.remote.ArticlesItem

class NewsSourceMapper {
    fun convertRemoteToCompact(data: List<ArticlesItem?>?):List<NewsSourceCompact>{
        val listSource:MutableList<NewsSourceCompact> = mutableListOf()
        data!!.forEach {
            if (!it!!.source!!.id.isNullOrEmpty()){
                val newsSourceCompact = NewsSourceCompact(it!!.source!!.id,it.source!!.name)
                listSource.add(newsSourceCompact)
                if(!listSource.contains(newsSourceCompact)){
                    listSource.add(newsSourceCompact)
                }
            }
        }
        return listSource
    }
}