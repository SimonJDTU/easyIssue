package com.example.easyissue

import android.content.Context
import androidx.lifecycle.MutableLiveData

class StateManager(context: Context) {

    private val token: String?= PreferenceHelper.defaultPrefs(context).getString("yeet",null)
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