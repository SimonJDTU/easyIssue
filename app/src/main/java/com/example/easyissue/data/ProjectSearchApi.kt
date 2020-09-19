package com.example.easyissue.data

import retrofit2.http.GET
import retrofit2.Call

interface ProjectSearchApi {

    @GET("/repos/:owner/:repo/projects")
    fun getProjects(): Call<ProjectSearchModel>

    companion object {
        const val API_URL = "https://api.github.com"
    }
}