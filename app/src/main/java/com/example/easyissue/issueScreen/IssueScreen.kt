package com.example.easyissue.issueScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.easyissue.R
import com.example.easyissue.databinding.LoginScreenBinding
import com.example.easyissue.loginScreen.LoginScreenViewModel

class IssueScreen : Fragment() {

    private lateinit var viewModel: LoginScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<LoginScreenBinding>(
            inflater,
            R.layout.login_screen, container, false
        )

        binding.viewModel = viewModel

        return binding.root
    }

}