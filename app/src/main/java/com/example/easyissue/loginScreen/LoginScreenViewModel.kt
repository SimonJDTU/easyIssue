package com.example.easyissue.loginScreen

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class LoginScreenViewModel : ViewModel() {
    var tokenErrorMsg: ObservableField<String> = ObservableField("")
    var tokenGuide: ObservableField<Boolean> = ObservableField(false)
    var isLoading: ObservableField<Boolean> = ObservableField(false)
}