package com.example.easyissue.preferences

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyissue.BaseActivity
import com.example.easyissue.PreferenceHelper.set
import com.example.easyissue.R
import com.example.easyissue.StateManager
import org.koin.core.KoinComponent
import org.koin.core.inject


class SettingsScreen : PreferenceFragmentCompat(), KoinComponent {

    private val stateManager: StateManager by inject()
    private lateinit var prefs: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when (preference.key) {
            getString(R.string.key_logout) -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

                builder.setTitle(resources.getString(R.string.settings_dialog_title))
                builder.setMessage(resources.getString(R.string.settings_dialog_body))

                builder.setPositiveButton(resources.getString(R.string.settings_dialog_positive)) { dialog, _ ->
                    stateManager.logout()
                    prefs[getString(R.string.key_token)] = ""
                    findNavController().navigate(SettingsScreenDirections.settingsScreenToLoginScreen())
                }

                builder.setNegativeButton(resources.getString(R.string.settings_dialog_negative)) { dialog, _ ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
                return true
            }
            getString(R.string.key_feedback) -> {
                (activity as BaseActivity).openEmailClient()
                return true
            }
            else -> return false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv: RecyclerView = listView
        rv.setPadding(0, resources.getDimension(R.dimen.unit2).toInt(), 0, 0)
    }
}