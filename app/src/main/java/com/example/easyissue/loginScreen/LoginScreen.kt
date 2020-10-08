package com.example.easyissue.loginScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.easyissue.R
import com.example.easyissue.SignInState
import com.example.easyissue.StateManager
import com.example.easyissue.databinding.LoginScreenBinding

class LoginScreen : Fragment() {

    private lateinit var viewModel: LoginScreenViewModel
    private val stateManager: StateManager =
        StateManager()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<LoginScreenBinding>(
            inflater,
            R.layout.login_screen, container, false)

        viewModel = ViewModelProvider(this).get(LoginScreenViewModel::class.java)

        binding.viewModel = viewModel
        viewModel.stateManager = stateManager

        stateManager.signInStateObservable.observe(viewLifecycleOwner, Observer {
            when (it) {
                is SignInState.Success -> {
                    findNavController().navigate(R.id.loginScreen_to_mainScreen)
                }
                is SignInState.Fail -> {
                    //TODO display error message
                }
            }
        })

        return binding.root
    }
}