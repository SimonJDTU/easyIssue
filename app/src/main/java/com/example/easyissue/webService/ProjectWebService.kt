package com.example.easyissue.webService

import com.example.easyissue.data.IssueDTO
import com.example.easyissue.data.IssueHolder
import com.example.easyissue.data.Project
import com.example.easyissue.data.UserDTO
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.http.*


object GithubWebService {
    //Creates client
    private val client: Retrofit = RetrofitFactory().getClient(RetrofitType.GITHUB)

    //Builds service
    private val service: GithubProjectApi = client.create(GithubProjectApi::class.java)

    fun getProjects(token: String): Single<List<Project>> {
        return service.getProjects("Bearer $token", "created", "internal")
            .subscribeOn(Schedulers.io())
    }

    fun getUser(token: String): Single<UserDTO>{
        return service.getUser("Bearer $token")
            .subscribeOn(Schedulers.io())
    }

    fun postIssue(userID: String, projectName: String, token: String, issueHolder: IssueHolder): Single<IssueDTO>{
        val issueBody: JsonObject = JsonParser.parseString(Gson().toJson(issueHolder)).asJsonObject

        return service.postIssue(userID, projectName, "Bearer $token", issueBody)
            .subscribeOn(Schedulers.io())
        //return service.postIssue(userID,projectName, "Bearer $token", issue).subscribeOn(Schedulers.io())
    }

    interface GithubProjectApi {

        @GET("/user")
        fun getUser(
            @Header("Authorization") token: String
        ): Single<UserDTO>

        @GET("/user/repos")
        fun getProjects(
            @Header("Authorization") token: String,
            @Query("sort") sortType: String,
            @Query("type") type: String
        ): Single<List<Project>>

        @POST("/repos/{user}/{project}/issues")
        fun postIssue(
            @Path("user") userID: String,
            @Path("project") projectName: String,
            @Header("Authorization") token: String,
            @Body issue: JsonObject
        ): Single<IssueDTO>
    }
}
