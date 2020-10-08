package com.example.easyissue

import androidx.lifecycle.MutableLiveData

class StateManager {
    //TODO: FIX
    private val token: String?= ""
    var signInStateObservable: MutableLiveData<SignInState> = MutableLiveData(SignInState.Idle)

    init {
        when (token) {
            is String -> {/*TODO: make user call*/}
            null -> {/*TODO: navigate to loginScreen*/}
        }
    }
}

sealed class SignInState {
    object Idle: SignInState()
    object Success: SignInState()
    data class Fail(val errorMsg: String): SignInState()
}