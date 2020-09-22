package com.example.easyissue.data

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.http.GET

object GithubWebService {

    private val client: Retrofit = getGithubClient()
    private val service: GithubProjectApi = client.buildService(GithubProjectApi::class.java)

    fun getProjects(): Single<List<Projects>> {
        return service.getProjects()
            .subscribeOn(Schedulers.io())
    }

    fun getUser(): Single<User>{
        return service.getUser()
            .subscribeOn(Schedulers.io())
    }

    interface GithubProjectApi {

        @GET("/user")
        fun getUser(): Single<User>

        @GET("/user/repos")
        fun getProjects(): Single<List<Projects>>
    }
}


