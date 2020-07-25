package com.raka.newslisttest.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raka.newslisttest.data.api.ApiService
import com.raka.newslisttest.utils.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class ApiServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var apiService: ApiService
    private lateinit var mockWebSerVer: MockWebServer
    private val CATEGORY = "entertainment"
    private val PAGE = "1"
    private val PAGE_SIZE = "7"
    @Before
    fun createService() {
        mockWebSerVer = MockWebServer()
        mockWebSerVer.start()
        val client = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebSerVer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
    @After
    fun stopService() {
        mockWebSerVer.shutdown()
    }
    @Test
    fun getProductListTest_passCorrectPath() {
        runBlocking {
            //Arrange
            setResponseSuccess("response.json")
            //Act
            val resultResponse = apiService.getSources(CATEGORY,PAGE_SIZE,PAGE,Constants.API_KEY).blockingGet()
            val request = mockWebSerVer.takeRequest()
            //Assert
            Assert.assertNotNull(resultResponse)
            Assert.assertThat(
                request.path,
                CoreMatchers.`is`("/everything?&q=entertainment&pageSize=7&page=1&apiKey=331b477e62ab40deb6e4cf97a1800149")
            )
        }
    }
    @Test
    fun loginTest_success_getCorrectResponse(){
        runBlocking {
            //Arrange
            setResponseSuccess("response.json")
            //Act
            val resultResponse = apiService.getSources(CATEGORY,PAGE_SIZE,PAGE,Constants.API_KEY).blockingGet()
            val response = resultResponse.status
            //Response
            Assert.assertThat("ok", `is`(response))
        }
    }
    private fun setResponseSuccess(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebSerVer.enqueue(mockResponse.setBody(source.readString(Charsets.UTF_8)))
    }
}