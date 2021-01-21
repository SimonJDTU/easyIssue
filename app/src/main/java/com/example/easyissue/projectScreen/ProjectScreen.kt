package com.example.easyissue.projectScreen

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyissue.R
import com.example.easyissue.SignInState
import com.example.easyissue.StateManager
import com.example.easyissue.data.Project
import com.example.easyissue.databinding.ProjectScreenBinding
import com.example.easyissue.webService.GithubWebService
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class ProjectScreen : Fragment(), KoinComponent, ProjectAdapter.OnItemClickListener {

    private lateinit var viewModel: ProjectScreenViewModel
    private lateinit var prefs: SharedPreferences
    private val stateManager: StateManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<ProjectScreenBinding>(
            inflater,
            R.layout.project_screen, container, false
        )

        viewModel = ViewModelProvider(this).get(ProjectScreenViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val isDoubleSpan = prefs.getBoolean("projectSpan", false)

        binding.projectList.layoutManager =
            GridLayoutManager(requireContext(), if (isDoubleSpan) 2 else 1)

        val projectAdapter = ProjectAdapter(this, isDoubleSpan)

        binding.adapter = projectAdapter

        binding.menuIcon.setOnClickListener {
            findNavController().navigate(R.id.projectScreen_to_settingsScreen)
        }

        stateManager.userState.observe(viewLifecycleOwner, {
            when (it) {
                is SignInState.ValidToken -> {
                    fetchProjects()
                }
                is SignInState.InvalidToken, SignInState.LoggedOut, SignInState.FreshStart -> {
                    findNavController().navigate(R.id.toLoginScreen)
                }
                else -> {
                }
            }
        })

        stateManager.validateToken()

        return binding.root
    }

    private fun fetchProjects() {
        val token: String = prefs.getString(getString(R.string.key_token), "").toString()

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
        return when (prefs.getString(getString(R.string.key_sortType), "")) {
            "sortType_created" -> data.sortedBy { list -> list.id }
            "sortType_alphabetical" -> data.sortedBy { list -> list.name.toLowerCase() }
            "sortType_lastEdited" -> data.sortedByDescending { list -> list.updatedAt }
            else -> data.sortedBy { list -> list.id }
        }
    }

    override fun onItemClick(item: Project) {
        val action = ProjectScreenDirections.projectScreenToIssueScreen(item)
        findNavController().navigate(action)
    }
}