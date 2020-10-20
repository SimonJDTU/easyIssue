package com.example.easyissue.projectScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easyissue.R
import com.example.easyissue.data.GithubWebService
import com.example.easyissue.databinding.ProjectScreenBinding
import com.example.easyissue.projectScreen.ProjectScreenViewModel.ListState.Alphabetical
import com.example.easyissue.projectScreen.ProjectScreenViewModel.ListState.LastEdited

class ProjectScreen : Fragment() {

    private lateinit var viewModel: ProjectScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ProjectScreenBinding>(
            inflater,
            R.layout.project_screen, container, false
        )

        viewModel = ViewModelProvider(this).get(ProjectScreenViewModel::class.java)

        binding.viewModel = viewModel

        val projectAdapter = ProjectAdapter()

        binding.adapter = projectAdapter

        viewModel.listState.observe(viewLifecycleOwner, Observer { listState ->
            viewModel.fetchedProjects.value?.let {
                when (listState) {
                    Alphabetical -> viewModel.fetchedProjects.postValue(it.sortedBy { list -> list.name })
                    LastEdited -> viewModel.fetchedProjects.postValue(it.sortedByDescending { list -> list.updatedAt })
                }
            }
        })

        //TODO: Implement using bindingAdapter "items"
        viewModel.fetchedProjects.observe(viewLifecycleOwner, Observer {
            it.let(projectAdapter::submitList)
        })

        fetchProjects()

        return binding.root
    }

    private fun fetchProjects() {
        GithubWebService.getProjects()
            .doOnSubscribe { viewModel.isLoading.set(true) }
            .doFinally { viewModel.isLoading.set(false) }
            /*.doOnError {
                showPlaceholder.set(true)
                showList.set(false)
            }*/
            .subscribe { data ->
                viewModel.fetchedProjects.postValue(data)
                viewModel.showList.set(true)
            }
    }
}