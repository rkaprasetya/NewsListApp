package com.raka.newslisttest.presentation.article

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raka.newslisttest.data.model.compact.ArticleCompact
import com.raka.newslisttest.domain.usecase.ArticleUsescase
import com.raka.newslisttest.utils.EventWrapper
import com.raka.newslisttest.utils.Util
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleViewModel @ViewModelInject constructor(private val usescase: ArticleUsescase):ViewModel() {
    private val TAG = this.javaClass.simpleName
    private var _listArticle: MutableLiveData<List<ArticleCompact>> = MutableLiveData()
    val listArticle: LiveData<List<ArticleCompact>>
        get() = _listArticle
    private var _isLoading : MutableLiveData<Boolean> = MutableLiveData()
    val isLoading : LiveData<Boolean>
        get() = _isLoading
    private val compositeDisposable= CompositeDisposable()
    private val _message = MutableLiveData<EventWrapper<String>>()
    val message : LiveData<EventWrapper<String>>
        get() = _message

    fun getArticles(category:String,source:String,page:String){
        viewModelScope.launch {
            val isInternetActive = withContext(Dispatchers.Default){
                Util.isInternetAvailable()
            }
            if (isInternetActive){
                loadArticles(category,source,page)
            }else{
                _message.value = EventWrapper("Please turn on your Internet connection")
            }
        }
    }

    private fun loadArticles(category: String, source: String, page: String) {
        _isLoading.value = true
        val result = usescase.fetchArticles(category,source,page)
            .subscribe({
                _listArticle.value = it
                _isLoading.value = false
            },{
                Log.e(TAG,"message : ${it.message}")
                _isLoading.value = false
            })
        compositeDisposable.add(result)
    }
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}