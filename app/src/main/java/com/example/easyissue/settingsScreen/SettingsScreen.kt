package com.example.easyissue.settingsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.easyissue.PreferenceHelper
import com.example.easyissue.PreferenceHelper.get
import com.example.easyissue.PreferenceHelper.set
import com.example.easyissue.R
import com.example.easyissue.databinding.SettingsScreenBinding

class SettingsScreen : Fragment() {

    private lateinit var binding: SettingsScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.settings_screen, container, false
        )

        //Checks in radiogroup the preferences selection
        binding.sortingGroup.check(
            when (PreferenceHelper.customPrefs(
                requireContext(),
                resources.getString(R.string.prefs_settings)
            )["projectSortType", ""]) {
                "created" -> R.id.created
                "alphabetical" -> R.id.alphabetical
                "lastEdited" -> R.id.lastEdited
                else -> R.id.created
            }
        )

        binding.sortingGroup.setOnCheckedChangeListener { _, checkedId ->
            var value: String? = null
            when (checkedId) {
                R.id.created -> value = "created"
                R.id.alphabetical -> value = "alphabetical"
                R.id.lastEdited -> value = "lastEdited"
            }
            PreferenceHelper.customPrefs(
                requireContext(),
                resources.getString(R.string.prefs_settings)
            )["projectSortType"] = value
        }

        //TODO: Implement dialog
        binding.logoutBtn.setOnClickListener {
            PreferenceHelper.customPrefs(
                requireContext(),
                resources.getString(R.string.prefs_login)
            )["token"] = null

            findNavController().popBackStack()
        }
        
        binding.closeBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    //Runs animation on fragment when popped from backstack
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        val navController = findNavController()
        val graph = navController.graph
        val dest = graph.findNode(R.id.projectScreen)
        if (!enter && dest != null && navController.currentDestination?.id == dest.id) {
            return AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
        }
        return super.onCreateAnimation(transit, enter, nextAnim)
    }
}