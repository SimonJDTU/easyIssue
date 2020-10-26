package com.example.easyissue.loginScreen

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.easyissue.StateManager

class LoginScreenViewModel : ViewModel() {
    val loginInfo: ObservableField<String> = ObservableField("")
    var isLoading: ObservableField<Boolean> = ObservableField(false)
    lateinit var stateManager: StateManager
}