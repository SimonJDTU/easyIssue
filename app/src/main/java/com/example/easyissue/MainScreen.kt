package com.example.easyissue

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

class MainScreen : Fragment() {

    companion object {
        fun newInstance() = MainScreen()
    }

    private lateinit var viewModel: MainScreenViewModel
    private lateinit var navigator: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainScreenViewModel::class.java)
        navigator = this.findNavController()

        navigator.navigate(R.id.mainScreen_to_loginScreen)
    }
}