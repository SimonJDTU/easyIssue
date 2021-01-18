package com.example.easyissue.loginScreen

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.easyissue.InteractiveDialog
import com.example.easyissue.InteractiveDialog.DialogType
import com.example.easyissue.InteractiveDialog.OnPositiveSelected
import com.example.easyissue.R
import com.example.easyissue.SignInState
import com.example.easyissue.StateManager
import com.example.easyissue.databinding.LoginScreenBinding
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.regex.Pattern


class LoginScreen : Fragment(), KoinComponent, OnPositiveSelected {

    private lateinit var viewModel: LoginScreenViewModel
    private val stateManager: StateManager by inject()

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

        stateManager.userState.observe(viewLifecycleOwner, {
            when (it) {
                is SignInState.ValidToken -> {
                    findNavController().navigate(R.id.loginScreen_to_projectScreen)
                }
                is SignInState.Fail -> {
                    binding.tokenInputLayout.error = getString(R.string.error_invalid_token)
                    viewModel.isLoading.set(false)
                }
                is SignInState.InvalidToken -> {
                    viewModel.isLoading.set(false)
                    InteractiveDialog(this, DialogType.Error()).show(
                        parentFragmentManager,
                        null
                    )
                }
                is SignInState.LoggedOut -> {
                    InteractiveDialog(this, DialogType.LoggedOut).show(
                        parentFragmentManager,
                        null
                    )
                }
                SignInState.FreshStart, SignInState.Idle -> {}
            }
        })

        binding.tokenGuideBtn.setOnClickListener {
            val boolean = viewModel.tokenGuide.get() as Boolean
            viewModel.tokenGuide.set(!boolean)

            binding.tokenGuide.apply {
                animation = if (boolean) {
                    AnimationUtils.loadAnimation(requireContext(), R.anim.text_down_out)
                } else {
                    AnimationUtils.loadAnimation(requireContext(), R.anim.text_up_in)
                }
                startAnimation(animation)
            }
        }

        binding.tokenInput.addTextChangedListener {
            binding.tokenInputLayout.error = null

            it?.let {
                if (it.length >= resources.getInteger(R.integer.MAX_TOKEN_LENGTH)) {
                    binding.tokenInput.apply {
                        clearFocus()
                        requireContext().hideKeyboard(this)
                    }
                }
            }
        }

        binding.loginBtn.setOnClickListener {
            val input = binding.tokenInput.text.toString()

            if (input.length != 40 || !validateInput(input)) {
                binding.tokenInputLayout.error = getString(R.string.error_bad_token, "Length")
            } else {
                stateManager.validateToken(input)
                viewModel.isLoading.set(true)
            }
        }

        return binding.root
    }

    private fun validateInput(input: String): Boolean {
        val PASSWORD_PATTERN = "[0-9a-fA-F]{40}"
        val pattern = Pattern.compile(PASSWORD_PATTERN)

        return pattern.matcher(input).matches()
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun dialogPositiveOption() {}
}