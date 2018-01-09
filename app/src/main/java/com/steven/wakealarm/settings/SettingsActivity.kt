package com.steven.wakealarm.settings

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import com.google.zxing.integration.android.IntentIntegrator
import com.steven.wakealarm.R
import com.steven.wakealarm.base.BaseActivity
import com.steven.wakealarm.utils.KEY_NFC_ID
import com.steven.wakealarm.utils.NFC_REQUEST_CODE

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
		if (barcodeResult?.contents != null) {
			prefs.edit().putString(getString(R.string.pref_key_barcode), barcodeResult.contents)
					.apply()
		} else if (requestCode == NFC_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			prefs.edit()
					.putString(getString(R.string.pref_key_nfc), data!!.getStringExtra(KEY_NFC_ID))
					.apply()
		}
	}
}
