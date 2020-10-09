package com.example.easyissue.projectScreen

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyissue.data.GithubWebService
import com.example.easyissue.data.Project

class ProjectScreenViewModel : ViewModel() {
    var fetchedProjects: MutableLiveData<List<Project>> = MutableLiveData(emptyList())
    var isLoading: ObservableField<Boolean> = ObservableField(false)

    init {
        getProjects()
    }

    private fun getProjects() {
        GithubWebService.getProjects()
            .doOnSubscribe{ isLoading.set(true) }
            .doFinally{ isLoading.set(false) }
            .subscribe { Projects ->
                fetchedProjects.postValue(Projects)
            }
    }
}