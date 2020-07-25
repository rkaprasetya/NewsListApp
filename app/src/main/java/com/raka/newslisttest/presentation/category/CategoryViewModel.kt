package com.raka.newslisttest.presentation.category

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raka.newslisttest.domain.usecase.CategoryUsecase

class CategoryViewModel @ViewModelInject constructor (usecase: CategoryUsecase):ViewModel() {
    private var _categoryList=MutableLiveData<List<String>>()
    val categoryList:LiveData<List<String>>
    get() = _categoryList
    init {
          _categoryList.value = usecase.fetchCategory()
    }
}