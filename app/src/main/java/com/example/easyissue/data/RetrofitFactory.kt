package com.example.easyissue.data

import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory: KoinComponent {
    private val GITHUB_BASE_URL = "https://api.github.com"
    private val GITLAB_BASE_URL = ""
    private val ANOTHERR_BASE_URL = ""

    fun getClient(type: RetrofitType): Retrofit {
        return Retrofit.Builder()
            .baseUrl(
                when (type) {
                    RetrofitType.GITHUB -> GITHUB_BASE_URL
                    RetrofitType.GITLAB -> GITLAB_BASE_URL
                    RetrofitType.OTHER -> ANOTHERR_BASE_URL
                }
            )
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor { chain ->
            val requestWithUserAgent = chain.request().newBuilder()
                //TODO: Dont push this
                .header(
                    "Authorization",
                    "Bearer" + " " + "token"
                )
                .build()
            chain.proceed(requestWithUserAgent)
        }.build()
    }
}

enum class RetrofitType{
    GITHUB, GITLAB, OTHER
}