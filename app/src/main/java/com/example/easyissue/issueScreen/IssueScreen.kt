package com.example.easyissue.issueScreen

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.example.easyissue.InteractiveDialog
import com.example.easyissue.InteractiveDialog.DialogType
import com.example.easyissue.InteractiveDialog.OnPositiveSelected
import com.example.easyissue.R
import com.example.easyissue.data.IssueHolder
import com.example.easyissue.data.Project
import com.example.easyissue.data.ProjectTheme
import com.example.easyissue.databinding.IssueScreenBinding
import com.example.easyissue.webService.GithubWebService
import timber.log.Timber

class IssueScreen : Fragment(), OnPositiveSelected {

    private lateinit var viewModel: IssueViewModel
    private lateinit var project: Project
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<IssueScreenBinding>(
            inflater,
            R.layout.issue_screen, container, false
        )

        viewModel = ViewModelProvider(this).get(IssueViewModel::class.java)

        prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val args: IssueScreenArgs by navArgs()

        project = args.projectItem

        viewModel.apply {
            val theme = ProjectTheme(project.language)

            topLogo = getDrawable(resources, theme.languageIcon, null)

            topGradient = getDrawable(resources, theme.topGradient, null)

            projectName = project.name
        }

        binding.viewModel = viewModel

        binding.sendBtn.setOnClickListener {
            InteractiveDialog(this, DialogType.Proceed).show(parentFragmentManager, null)
        }

        binding.titleInput.doAfterTextChanged {
            viewModel.issueTitle = it.toString()
            if (it.isNullOrBlank()) {
                binding.titleInputLayout.error = getString(R.string.error_empty_title)
            }
        }

        binding.bodyInput.doAfterTextChanged {
            viewModel.issueBody = it.toString()
        }

        return binding.root
    }

    override fun dialogPositiveOption() {
        val issueTitle = viewModel.issueTitle.toString()
        val issueBody = viewModel.issueBody.toString()
        val token = prefs.getString(getString(R.string.key_token), "").toString()

        viewModel.isLoading.set(true)

        GithubWebService.postIssue(
            project.owner.login, project.name, token, IssueHolder(
                issueTitle,
                issueBody
            )
        ).subscribe { data, throwable ->
            viewModel.isLoading.set(false)
            if (throwable == null) {
                Timber.e(data.toString())
                val action = IssueScreenDirections.issueScreenToConfirmationScreen(data)
                findNavController().navigate(action)
            } else {
                InteractiveDialog(this, DialogType.Error(throwable)).show(
                    parentFragmentManager,
                    null
                )
            }
        }
    }
}