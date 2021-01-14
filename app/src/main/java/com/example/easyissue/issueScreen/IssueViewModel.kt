package com.example.easyissue.issueScreen

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel

class IssueViewModel : ViewModel() {
    var issueTitle: String? = ""
    var issueBody: String? = ""
    var topLogo: Drawable? = null
    var topGradient: Drawable? = null
    lateinit var projectName: String

}