package com.example.easyissue

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.easyissue.data.*

class LoginScreenViewModel : ViewModel() {
    val loginInfo: ObservableField<String> = ObservableField("")
    var isLoading: ObservableField<Boolean> = ObservableField(false)

    fun getUser(){
        GithubWebService.getUser()
            .doOnSubscribe{ isLoading.set(true) }
            .doFinally{ isLoading.set(false) }
            .subscribe { User ->
                loginInfo.set(User.toString())
            }
    }

    fun getProjects() {
        GithubWebService.getProjects()
            .doOnSubscribe{ isLoading.set(true) }
            .doFinally{ isLoading.set(false) }
            .subscribe { Projects ->
                loginInfo.set(Projects.toString())
            }
    }
}