package com.example.easyissue.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GithubWebService {

    private val client: GithubProjectApi = getGithubClient().buildService(GithubProjectApi::class.java)

    fun getProjects() {
        val call = client.getProjects()

        call.enqueue(object : Callback<List<ProjectSearchModels>> {
            override fun onResponse(
                call: Call<List<ProjectSearchModels>>,
                response: Response<List<ProjectSearchModels>>
            ) {
                if (response.isSuccessful) {

                }
            }

            override fun onFailure(call: Call<List<ProjectSearchModels>>, t: Throwable) {
                t.message.let {

                }
            }
        })
    }

    fun getUser(){
        val call = client.getUser()

        call.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                if (response.isSuccessful) {

                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.message.let {

                }
            }
        })
    }
}


