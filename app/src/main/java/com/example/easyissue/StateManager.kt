package com.example.easyissue

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.easyissue.PreferenceHelper.get
import com.example.easyissue.PreferenceHelper.set
import com.example.easyissue.data.GithubWebService

class StateManager(val context: Context) {

    private val prefs = PreferenceHelper
    var userState: MutableLiveData<SignInState> = MutableLiveData(SignInState.Idle)

    fun validateToken(tokenInput: String? = "") {
        val token = if(tokenInput.isNullOrBlank()){
            prefs.customPrefs(context, context.resources.getString(R.string.prefs_login))["token"]
        }else{
            tokenInput
        }

        GithubWebService.getUser(token)
            .subscribe {_, throwable ->
                if(throwable == null){
                    prefs.customPrefs(context, context.resources.getString(R.string.prefs_login))["token"] = token
                    userState.postValue(SignInState.Success)
                }else{
                    prefs.customPrefs(context,context.resources.getString(R.string.prefs_login))["token"] = null
                    userState.postValue( SignInState.Fail(throwable.message.toString()) )
                }
            }
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
    object Success : SignInState()
    data class Fail(val errorMsg: String) : SignInState()
}