package com.steven.wakealarm

import android.app.Application
import android.preference.PreferenceManager
import com.steven.wakealarm.utils.PREFS_VERSION_CODE

class AlarmApp : Application() {

	override fun onCreate() {
		super.onCreate()
		val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
		val versionCode = sharedPreferences.getInt(PREFS_VERSION_CODE, BuildConfig.VERSION_CODE)

		if (versionCode != BuildConfig.VERSION_CODE) {
			onUpdate()
		}
		sharedPreferences.edit().putInt(PREFS_VERSION_CODE, BuildConfig.VERSION_CODE).apply()
	}

	private fun onUpdate() {
	}
}
