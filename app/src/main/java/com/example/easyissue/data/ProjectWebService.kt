package com.example.easyissue.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ProjectWebService {

    init {
        retrofitInstance = createRetrofitInstance()
    }

    private fun createRetrofitInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(ProjectSearchApi.API_URL)
            .client(getHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun getHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor { chain ->
            val requestWithUserAgent = chain.request().newBuilder()
                .header("User-Agent", "My custom user agent")
                .build()
            chain.proceed(requestWithUserAgent)
        }
        return okHttpBuilder.build()
    }

    companion object{
        lateinit var retrofitInstance : Retrofit
    }
}
