package com.wagoodman.stackattack.data

import android.os.Bundle
import android.preference.PreferenceActivity
import com.wagoodman.stackattack.R

class AppPreferences : PreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences)
    }
}