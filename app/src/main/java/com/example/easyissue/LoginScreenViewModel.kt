package com.example.easyissue

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.easyissue.SignInState.*
import com.example.easyissue.data.*

class LoginScreenViewModel : ViewModel() {
    val loginInfo: ObservableField<String> = ObservableField("")
    var isLoading: ObservableField<Boolean> = ObservableField(false)
    lateinit var stateManager: StateManager

    fun validateToken(){
        GithubWebService.getUser()
            .doOnSubscribe{ isLoading.set(true) }
            .doFinally{ isLoading.set(false) }
            .doOnError {t ->
                stateManager.signInStateObservable.postValue(Fail(t.toString()))
            }.subscribe { _ ->
                stateManager.signInStateObservable.postValue(Success)
            }
    }
}