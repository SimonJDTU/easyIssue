package com.example.easyissue.projectScreen

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyissue.data.Project

class ProjectScreenViewModel : ViewModel() {
    var fetchedProjects: MutableLiveData<List<Project>> = MutableLiveData(emptyList())
    var isLoading: ObservableField<Boolean> = ObservableField(false)
    var showList: ObservableField<Boolean> = ObservableField(false)
    var showPlaceholder: ObservableField<Boolean> = ObservableField(false)
}