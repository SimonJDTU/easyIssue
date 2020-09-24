package com.example.easyissue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.easyissue.databinding.LoginScreenBinding

class LoginScreen : Fragment() {

    private lateinit var viewModel: LoginScreenViewModel
    private lateinit var stateManager: StateManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<LoginScreenBinding>(
            inflater, R.layout.login_screen, container, false)

        //TODO: FIX
        stateManager = context?.let { StateManager(it) }!!

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