package com.steven.wakealarm.settings

import android.os.Bundle
import com.steven.wakealarm.R
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

	override fun onCreatePreferencesFix(savedInstanceState: Bundle?, rootKey: String?) {
		setPreferencesFromResource(R.xml.preference, rootKey)
	}
}
