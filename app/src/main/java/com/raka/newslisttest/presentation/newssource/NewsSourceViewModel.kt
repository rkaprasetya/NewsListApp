package com.raka.newslisttest.presentation.newssource

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raka.newslisttest.data.model.compact.NewsSourceCompact
import com.raka.newslisttest.domain.usecase.NewsSourceUsecase
import com.raka.newslisttest.utils.EventWrapper
import com.raka.newslisttest.utils.Util
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsSourceViewModel @ViewModelInject constructor(private val usecase: NewsSourceUsecase):ViewModel() {
    private val TAG = this.javaClass.simpleName
    private var _listSource:MutableLiveData<List<NewsSourceCompact>> = MutableLiveData()
    val listSource:LiveData<List<NewsSourceCompact>>
    get() = _listSource
    private var _isLoading : MutableLiveData<Boolean> = MutableLiveData()
    val isLoading : LiveData<Boolean>
    get() = _isLoading
    private val compositeDisposable=CompositeDisposable()
    private val _message = MutableLiveData<EventWrapper<String>>()
    val message : LiveData<EventWrapper<String>>
        get() = _message
    fun getNewsSource(category:String,page:String){
        viewModelScope.launch {
         val isInternetActive = withContext(Dispatchers.Default){
             Util.isInternetAvailable()
         }
            if (isInternetActive){
                loadNewsSource(category,page)
            }else{
                _message.value = EventWrapper("Please turn on your Internet connection")
            }
        }
    }
    fun loadNewsSource(category:String,page:String){
        _isLoading.value = true
        val result = usecase.fetchNewsSources(category,page)
            .subscribe({
                _listSource.value = it
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