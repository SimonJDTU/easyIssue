package com.example.easyissue.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val GITHUB_BASE_URL = "https://api.github.com"

fun getGithubClient(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(GITHUB_BASE_URL)
        .client(getHttpClient())
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

private fun getHttpClient(): OkHttpClient {
    val okHttpBuilder = OkHttpClient.Builder()
    okHttpBuilder.addInterceptor { chain ->
        val requestWithUserAgent = chain.request().newBuilder()
            //TODO: Dont push this
            .header("Authorization", "Bearer" + " " + "29bd93bfd8e6edb7790676322518cac2b7a72652")
            .build()
        chain.proceed(requestWithUserAgent)
    }
    return okHttpBuilder.build()
}