package com.example.easyissue.settingsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
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

        binding.filledExposedDropdown.apply {

            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.dropdown_menu_popup_item,
                resources.getTextArray(R.array.sortTypes)
            )

            setAdapter(adapter)

            setText(
                resources.getString(
                    when (PreferenceHelper.customPrefs(
                        requireContext(),
                        resources.getString(R.string.prefs_settings)
                    )["projectSortType", ""]) {
                        "created" -> R.string.created
                        "alphabetical" -> R.string.alphabetical
                        "lastEdited" -> R.string.lastEdited
                        else -> R.string.created
                    }
                ), false
            )

            setOnItemClickListener { _, _, position, _ ->
                PreferenceHelper.customPrefs(
                    requireContext(),
                    resources.getString(R.string.prefs_settings)
                )["projectSortType"] =
                    when (position) {
                        0 -> "created"
                        1 -> "alphabetical"
                        2 -> "lastEdited"
                        else -> "created"
                    }
            }
        }

        binding.spanGroup.apply {

            check(when(PreferenceHelper.customPrefs(
                requireContext(),
                resources.getString(R.string.prefs_settings)
            )["isSingleSpan", true]){
                true -> R.id.span1
                false -> R.id.span2
            })

            setOnCheckedChangeListener { _, checkedId ->
                var value: Boolean? = null
                when (checkedId) {
                    R.id.span1 -> value = true
                    R.id.span2 -> value = false
                }
                PreferenceHelper.customPrefs(
                    requireContext(),
                    resources.getString(R.string.prefs_settings)
                )["isSingleSpan"] = value

            }
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