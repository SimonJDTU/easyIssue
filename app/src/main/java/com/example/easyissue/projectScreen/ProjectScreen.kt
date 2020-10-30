package com.example.easyissue.projectScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyissue.PreferenceHelper
import com.example.easyissue.PreferenceHelper.get
import com.example.easyissue.R
import com.example.easyissue.SignInState
import com.example.easyissue.StateManager
import com.example.easyissue.data.GithubWebService
import com.example.easyissue.data.Project
import com.example.easyissue.databinding.ProjectScreenBinding
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class ProjectScreen : Fragment(), KoinComponent, ProjectAdapter.OnItemClickListener {

    private lateinit var viewModel: ProjectScreenViewModel
    private val prefs = PreferenceHelper
    private val stateManager: StateManager by inject()

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
        binding.lifecycleOwner = viewLifecycleOwner

        val isSingleSpan: Boolean = prefs.customPrefs(
            requireContext(),
            resources.getString(R.string.prefs_settings)
        )["isSingleSpan", true]

        binding.projectList.layoutManager = GridLayoutManager(requireContext(), if(isSingleSpan) 1 else 2)

        val projectAdapter = ProjectAdapter(this, isSingleSpan, resources)

        binding.adapter = projectAdapter

        binding.menuIcon.setOnClickListener {
            findNavController().navigate(R.id.projectScreen_to_settingsScreen)
        }

        stateManager.userState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is SignInState.ValidToken -> {
                    fetchProjects()
                }
                is SignInState.InvalidToken -> {
                    findNavController().navigate(R.id.projectSceen_to_loginScreen)
                }
                else -> {}
            }
        })

        stateManager.validateToken()

        return binding.root
    }

    private fun fetchProjects() {
        val token = prefs.customPrefs(
            requireContext(),
            resources.getString(R.string.prefs_login)
        )["token", ""]

        GithubWebService.getProjects(token)
            .doOnSubscribe {
                viewModel.apply {
                    isLoading.set(true)
                    showList.set(false)
                }
            }.doOnError {
                viewModel.apply {
                    isLoading.set(false)
                    showPlaceholder.set(true)
                }
            }.doOnSuccess {
                viewModel.apply {
                    isLoading.set(false)
                    showList.set(true)
                }
            }.subscribe { data, throwable ->
                if (throwable == null) {
                    viewModel.fetchedProjects.postValue(sortProjects(data))
                } else {
                    Timber.e(throwable.message.toString())
                }
            }
    }

    private fun sortProjects(data: List<Project>): List<Project> {
        return when (PreferenceHelper.customPrefs(
            requireContext(),
            resources.getString(R.string.prefs_settings)
        )["projectSortType", ""]) {
            "created" -> data.sortedBy { list -> list.id }
            "alphabetical" -> data.sortedBy { list -> list.name }
            "lastEdited" -> data.sortedByDescending { list -> list.updatedAt }
            else -> data.sortedBy { list -> list.id }
        }
    }

    override fun onItemClick(item: Project) {
        val action = ProjectScreenDirections.projectScreenToIssueScreen(item)
        findNavController().navigate(action)
    }
}