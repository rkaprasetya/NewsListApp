package com.raka.newslisttest.utils

open class EventWrapper<out T>(private val content: T) {
    var hasBeenHandled = false
        private set // Allow external read but not write
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
    fun peekContent(): T = content
}