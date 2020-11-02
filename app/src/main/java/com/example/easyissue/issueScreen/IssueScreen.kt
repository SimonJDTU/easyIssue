package com.example.easyissue.issueScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.easyissue.R
import com.example.easyissue.data.Project
import com.example.easyissue.data.ProjectTheme
import com.example.easyissue.databinding.IssueScreenBinding

class IssueScreen : Fragment() {

    private lateinit var viewModel: IssueViewModel
    private lateinit var project: Project

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<IssueScreenBinding>(
            inflater,
            R.layout.issue_screen, container, false
        )

        viewModel = ViewModelProvider(this).get(IssueViewModel::class.java)

        val args: IssueScreenArgs by navArgs()

        project = args.projectItem

        binding.apply {
            val theme = ProjectTheme(project.language)

            topLogo.setImageDrawable(
                getDrawable(resources,theme.languageIcon,null)
            )

            topGradient.background =
                getDrawable(resources,theme.topGradient, null)

            projectName.text = project.name
        }

        binding.viewModel = viewModel

        return binding.root
    }

}