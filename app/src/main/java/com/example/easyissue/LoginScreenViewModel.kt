package com.example.easyissue

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.easyissue.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginScreenViewModel : ViewModel() {
    var loginInfo: ObservableField<String> = ObservableField("nothing received")

    fun setLoginInfo(msg: String){
        loginInfo.set(msg)
    }

    fun getUser(){
        GithubWebService.getUser()
    }

    fun getProjects() {
        GithubWebService.getProjects()
    }
}