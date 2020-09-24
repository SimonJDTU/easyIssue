package com.example.easyissue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.easyissue.databinding.MainScreenBinding

class MainScreen : Fragment() {

    private lateinit var viewModel: MainScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<MainScreenBinding>(
            inflater, R.layout.main_screen, container, false)

        viewModel = ViewModelProvider(this).get(MainScreenViewModel::class.java)

        binding.viewModel = viewModel

        return binding.root
    }
}