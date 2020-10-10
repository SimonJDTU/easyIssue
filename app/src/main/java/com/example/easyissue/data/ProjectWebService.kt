package com.example.easyissue.data

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.http.GET

object GithubWebService {
    //Creates client
    private val client: Retrofit = RetrofitFactory().getClient(RetrofitType.GITHUB)

    //Builds service
    private val service: GithubProjectApi = client.create(GithubProjectApi::class.java)

    fun getProjects(): Single<List<Project>> {
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
        fun getProjects(): Single<List<Project>>
    }
}
