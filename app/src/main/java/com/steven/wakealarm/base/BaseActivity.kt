package com.steven.wakealarm.base

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.steven.wakealarm.R

abstract class BaseActivity : AppCompatActivity() {

	protected val TAG: String = javaClass.simpleName
	protected lateinit var prefs: SharedPreferences
	protected var theme: String? = null
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		prefs = PreferenceManager.getDefaultSharedPreferences(this)
		theme = prefs.getString(getString(R.string.pref_key_theme), "light")
		setTheme(if (theme == "dark") R.style.DarkTheme else R.style.LightTheme)
		Log.d(TAG, "Activity Created")
	}

	override fun onPause() {
		super.onPause()
		Log.d(TAG, "Activity Paused")
	}

	override fun onResume() {
		super.onResume()
		Log.d(TAG, "Activity Resumed")
	}

	override fun onDestroy() {
		super.onDestroy()
		Log.d(TAG, "Activity Destroyed")
	}
}
