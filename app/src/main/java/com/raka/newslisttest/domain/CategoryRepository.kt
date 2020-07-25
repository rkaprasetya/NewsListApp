package com.raka.newslisttest.domain

interface CategoryRepository {
    fun loadCategory():List<String>
}