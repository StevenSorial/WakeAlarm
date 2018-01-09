package com.steven.wakealarm.settings

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.preference.PreferenceManager
import com.steven.wakealarm.R
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {

	private val prefs: SharedPreferences by lazy {
		PreferenceManager.getDefaultSharedPreferences(context)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		prefs.registerOnSharedPreferenceChangeListener(this)
	}

	override fun onCreatePreferencesFix(savedInstanceState: Bundle?, rootKey: String?) {
		setPreferencesFromResource(R.xml.preference, rootKey)
	}

	override fun onDestroy() {
		super.onDestroy()
		prefs.unregisterOnSharedPreferenceChangeListener(this)
	}

	override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String) {
		if (key == getString(R.string.pref_key_barcode) || key == getString(R.string.pref_key_nfc)) {
			val preference = findPreference(key)
			if (preference is ClickPreference) {
				preference.value = sharedPreferences?.getString(key, null)
				preference.summary = preference.summary
			}
		}
	}
}
