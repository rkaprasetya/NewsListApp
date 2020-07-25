package com.raka.newslisttest.data.repository

import com.raka.newslisttest.domain.CategoryRepository

class CategoryRepositoryImpl:CategoryRepository {
    override fun loadCategory(): List<String> = listOf("Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology")
}