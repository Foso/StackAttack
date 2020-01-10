package com.wagoodman.stackattack.ui

import android.app.Activity
import android.os.Bundle
import android.preference.PreferenceFragment
import com.wagoodman.stackattack.R

class AppPreferencesFragment : Activity() {
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        // Display the fragment as the main content.
        fragmentManager.beginTransaction().replace(android.R.id.content,
                PrefsFragment()).commit()
    }

    class PrefsFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle) {
            super.onCreate(savedInstanceState)
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences)
        }
    }
}