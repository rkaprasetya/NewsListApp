package com.raka.newslisttest.utils

import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UtilTest {
    @Test
    fun isInternetAvailable_success_returnTrue(){
        runBlocking {
            val result = Util.isInternetAvailable()
            Assert.assertThat(true, `is`(result))
        }
    }
    @Test
    fun isInternetAvailable_failure_returnFalse(){
        runBlocking {
            val result = Util.isInternetAvailable()
            Assert.assertThat(false, `is`(result))
        }
    }
}