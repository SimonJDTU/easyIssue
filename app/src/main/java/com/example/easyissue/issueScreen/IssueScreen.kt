package com.example.easyissue.issueScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.easyissue.R
import com.example.easyissue.databinding.IssueScreenBinding

class IssueScreen : Fragment() {

    private lateinit var viewModel: IssueViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<IssueScreenBinding>(
            inflater,
            R.layout.issue_screen, container, false
        )

        viewModel = ViewModelProvider(this).get(IssueViewModel::class.java)

        binding.viewModel = viewModel

        return binding.root
    }

}