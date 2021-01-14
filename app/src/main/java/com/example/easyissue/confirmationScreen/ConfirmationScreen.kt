package com.example.easyissue.confirmationScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.easyissue.R
import com.example.easyissue.data.Issue
import com.example.easyissue.databinding.ConfirmationScreenBinding

class ConfirmationScreen : Fragment() {

    private lateinit var issueData: Issue
    private lateinit var viewModel: ConfirmationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ConfirmationScreenBinding>(
            inflater,
            R.layout.confirmation_screen, container, false
        )

        viewModel = ViewModelProvider(this).get(ConfirmationViewModel::class.java)

        binding.viewModel = viewModel

        val args: ConfirmationScreenArgs by navArgs()

        issueData = args.IssueData

        binding.checkmarkAnimation.playAnimation()

        viewModel.apply {
            informationSting.set(
                resources.getString(
                    R.string.confirmation_issueData,
                    issueData.number.toString(),
                    issueData.title
                )
            )
        }

        binding.projectsBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}