package com.steven.wakealarm.settings

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import com.google.zxing.integration.android.IntentIntegrator
import com.steven.wakealarm.R
import com.steven.wakealarm.base.BaseActivity

class SettingsActivity : BaseActivity(), OnSharedPreferenceChangeListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_settings)
		prefs.registerOnSharedPreferenceChangeListener(this)
	}

	override fun onDestroy() {
		super.onDestroy()
		prefs.unregisterOnSharedPreferenceChangeListener(this)
	}

	override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
		when (key) {
			getString(R.string.pref_key_theme) -> finish()
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		val barcodeResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
		if (barcodeResult != null) {
			if (barcodeResult.contents != null) {
				prefs.edit().putString(getString(R.string.pref_key_barcode), barcodeResult.contents)
						.apply()
			}
		}
	}
}
