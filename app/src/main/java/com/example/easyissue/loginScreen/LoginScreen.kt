package com.example.easyissue.loginScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.easyissue.R
import com.example.easyissue.SignInState
import com.example.easyissue.StateManager
import com.example.easyissue.databinding.LoginScreenBinding
import io.reactivex.plugins.RxJavaPlugins
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import java.util.regex.Pattern


class LoginScreen : Fragment(), KoinComponent {

    private lateinit var viewModel: LoginScreenViewModel
    private val stateManager: StateManager by inject()

    init {
        RxJavaPlugins.setErrorHandler(Timber::e)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<LoginScreenBinding>(
            inflater,
            R.layout.login_screen, container, false
        )

        viewModel = ViewModelProvider(this).get(LoginScreenViewModel::class.java)

        binding.viewModel = viewModel
        viewModel.stateManager = stateManager

        stateManager.userState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is SignInState.Success -> {
                    findNavController().navigate(R.id.loginScreen_to_projectScreen)
                }
                is SignInState.Fail -> {
                    //TODO display error message
                    Toast.makeText(requireContext(), "Sign in Failed", Toast.LENGTH_LONG).show()
                }
                else -> Toast.makeText(requireContext(), "else in when hit", Toast.LENGTH_LONG)
                    .show()
            }
        })

        binding.loginBtn.setOnClickListener {
            val input = binding.tokenInput.text.toString()
            if(validateInput(input)){
                stateManager.validateToken(input)
            }else{
                //TODO: Snackbar?
                Toast.makeText(requireContext(),"Password regex failed", Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    private fun validateInput(input: String): Boolean {
        val PASSWORD_PATTERN = "[0-9a-fA-F]{40}"
        val pattern = Pattern.compile(PASSWORD_PATTERN)

        return pattern.matcher(input).matches()
    }
}