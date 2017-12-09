package com.steven.wakealarm.settings

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import com.steven.wakealarm.R
import com.steven.wakealarm.base.BaseActivity

class SettingsActivity : BaseActivity(), OnSharedPreferenceChangeListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_settings)
		prefs?.registerOnSharedPreferenceChangeListener(this)
	}

	override fun onDestroy() {
		super.onDestroy()
		prefs?.unregisterOnSharedPreferenceChangeListener(this)
	}

	override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
		if (key == getString(R.string.pref_key_theme)) {
			finish()
		}
	}
}
