package com.example.easyissue.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val GITHUB_BASE_URL = "https://api.github.com"

fun getGithubClient(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(GITHUB_BASE_URL)
        .client(getHttpClient())
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(ArrayListAdapter)
                    .build()
            )
        )
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
            .header("Authorization", "Bearer" + " " + "Token")
            .build()
        chain.proceed(requestWithUserAgent)
    }
    return okHttpBuilder.build()
}

private object ArrayListAdapter {
    @ToJson
    fun arrayListToJson(list: ArrayList<ProjectSearchModels>): List<ProjectSearchModels> = list

    @FromJson
    fun arrayListFromJson(list: List<ProjectSearchModels>): ArrayList<ProjectSearchModels> = ArrayList(list)
}