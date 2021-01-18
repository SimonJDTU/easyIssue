package com.example.easyissue.issueScreen

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.example.easyissue.R
import com.example.easyissue.webService.GithubWebService
import com.example.easyissue.data.IssueHolder
import com.example.easyissue.data.Project
import com.example.easyissue.data.ProjectTheme
import com.example.easyissue.databinding.IssueScreenBinding
import com.example.easyissue.issueScreen.InteractiveDialog.DialogType
import kotlinx.android.synthetic.main.dialog_confirmation.view.*
import kotlinx.android.synthetic.main.dialog_error.view.*
import timber.log.Timber

class IssueScreen : Fragment(), InteractiveDialog.OnYesSelected {

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
        }

        binding.bodyInput.doAfterTextChanged {
            viewModel.issueBody = it.toString()
        }

        return binding.root
    }

    override fun queryPostIssue() {
        val issueTitle = viewModel.issueTitle.toString()
        val issueBody = viewModel.issueBody.toString()
        val token = prefs.getString(getString(R.string.key_token), "").toString()

        GithubWebService.postIssue(
            project.owner.login, project.name, token, IssueHolder(
                issueTitle,
                issueBody
            )
        ).subscribe { data, throwable ->
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

class InteractiveDialog(private val listener: OnYesSelected, private val dialogType: DialogType) :
    DialogFragment() {

    interface OnYesSelected {
        fun queryPostIssue()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return when (dialogType) {
            is DialogType.Proceed -> {
                inflater.inflate(R.layout.dialog_confirmation, container, false)
            }
            is DialogType.Error -> {
                inflater.inflate(R.layout.dialog_error, container, false)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupClickListeners(view: View) {
        when (dialogType) {
            is DialogType.Proceed -> {
                view.btnPositive.setOnClickListener {
                    listener.queryPostIssue()
                    dismiss()
                }
                view.btnNegative.setOnClickListener {
                    dismiss()
                }
            }
            is DialogType.Error -> {
                view.btnProceed.setOnClickListener {
                    dismiss()
                }
            }
        }
    }

    sealed class DialogType {
        object Proceed : DialogType()
        data class Error(val errorMsg: Throwable) : DialogType()
    }
}

