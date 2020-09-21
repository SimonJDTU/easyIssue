package com.example.easyissue.data

import retrofit2.http.GET
import retrofit2.Call

interface GithubProjectApi {

    @GET("/user")
    fun getUser(): Call<User>

    @GET("/user/repos")
    fun getProjects(): Call<List<ProjectSearchModels>>
}