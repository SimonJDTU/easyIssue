package com.example.easyissue.preferences

import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.easyissue.R


class MySettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        if (preference.key == "logoutBtn") {
            Toast.makeText(requireContext(),"yeet",Toast.LENGTH_LONG).show()
            return true
        }
        return false
    }
}