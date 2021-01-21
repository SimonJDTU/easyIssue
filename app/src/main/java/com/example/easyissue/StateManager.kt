package com.example.easyissue

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.example.easyissue.PreferenceHelper.set
import com.example.easyissue.webService.GithubWebService

class StateManager(val context: Context) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    var userState: MutableLiveData<SignInState> = MutableLiveData(SignInState.Idle)

    fun validateToken(tokenInput: String? = "") {
        val token = if (tokenInput.isNullOrBlank()) {
            prefs.getString(context.resources.getString(R.string.key_token), "").toString()
        } else {
            tokenInput
        }

        if (token.isEmpty()) {
            userState.postValue(SignInState.FreshStart)
        } else {
            GithubWebService.getUser(token)
                .subscribe { _, throwable ->
                    if (throwable == null) {
                        prefs[context.resources.getString(R.string.key_token)] = token
                        userState.postValue(SignInState.ValidToken)
                    } else {
                        prefs[context.resources.getString(R.string.key_token)] = null
                        userState.postValue(SignInState.InvalidToken(throwable.message.toString()))
                    }
                }
        }
    }

    fun logout() {
        userState.postValue(SignInState.LoggedOut)
    }
}


/*
User states which determines actions from observers:
- Idle is default state when user state hasn't been determined
- Success is state when login is successful
- Fail is state when login is unsuccessful
 */
sealed class SignInState {
    object Idle : SignInState()
    object ValidToken : SignInState()
    object LoggedOut : SignInState()
    object FreshStart : SignInState()
    data class InvalidToken(val errorMsg: String) : SignInState()
    data class Fail(val errorMsg: String) : SignInState()
}