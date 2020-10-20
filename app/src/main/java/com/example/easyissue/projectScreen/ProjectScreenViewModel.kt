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
    var listState: MutableLiveData<ListState> = MutableLiveData(ListState.Alphabetical)

    fun sortList(type: Int){
        when(type){
            1 -> listState.value = ListState.Alphabetical
            2 -> listState.value = ListState.LastEdited
        }
    }

    sealed class ListState{
        object Alphabetical: ListState()
        object LastEdited: ListState()
    }
}