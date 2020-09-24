package com.example.easyissue

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.easyissue.data.GithubWebService

class MainScreenViewModel : ViewModel() {
    val projectInfo: ObservableField<String> = ObservableField("")
    var isLoading: ObservableField<Boolean> = ObservableField(false)

    init {
        getProjects()
    }

    private fun getProjects() {
        GithubWebService.getProjects()
            .doOnSubscribe{ isLoading.set(true) }
            .doFinally{ isLoading.set(false) }
            .subscribe { Projects ->
                projectInfo.set(Projects.toString())
            }
    }

}