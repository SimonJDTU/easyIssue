package com.example.easyissue.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val GITHUB_BASE_URL = "https://api.github.com"
private val okHttpClient: OkHttpClient by lazy {
    OkHttpClient.Builder().addInterceptor { chain ->
        val requestWithUserAgent = chain.request().newBuilder()
            //TODO: Dont push this
            .header("Authorization", "Bearer" + " " + "Token")
            .build()
        chain.proceed(requestWithUserAgent)
    }.build()
}

fun getGithubClient(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(GITHUB_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
}

fun getGitlabClient(): Retrofit? {
    return null
}

fun getAnotherClient(): Retrofit? {
    return null
}

fun<T> Retrofit.buildService(service: Class<T>): T{
    return this.create(service)
}