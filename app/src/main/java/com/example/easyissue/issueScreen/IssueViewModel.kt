package com.example.easyissue.issueScreen

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class IssueViewModel : ViewModel() {
    var issueTitle: String? = ""
    var issueBody: String? = ""
    var topLogo: Drawable? = null
    var topGradient: Drawable? = null
    var isLoading: ObservableField<Boolean> = ObservableField(false)
    lateinit var projectName: String

}