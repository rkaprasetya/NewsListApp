package com.raka.newslisttest.domain.usecase

import com.raka.newslisttest.domain.CategoryRepository
import javax.inject.Inject

class CategoryUsecase @Inject constructor(private val repository: CategoryRepository) {
    fun fetchCategory():List<String> = repository.loadCategory()
}