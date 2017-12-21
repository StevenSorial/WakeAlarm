package com.steven.wakealarm

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.WindowManager
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.steven.wakealarm.base.BaseActivity
import com.steven.wakealarm.utils.PREFS_CHALLENGE
import kotlinx.android.synthetic.main.activity_alarm.*

class AlarmActivity : BaseActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_alarm)
		window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
		window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
		val prefs = PreferenceManager.getDefaultSharedPreferences(this)
		btn_dismiss.setOnClickListener {
			val challenge = prefs.getInt(PREFS_CHALLENGE, 0)
			when (challenge) {
				0 -> stopAlarm()
				1 -> scanBarcode()
			}
		}
	}

	private fun stopAlarm() {
		val intent = Intent(this, AlarmService::class.java)
		stopService(intent)
		onBackPressed()
	}

	private fun scanBarcode() {
		IntentIntegrator(this).initiateScan()
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		//val barcodeResult =
		when (requestCode) {
			IntentIntegrator.REQUEST_CODE ->
				checkBarcode(IntentIntegrator.parseActivityResult(requestCode, resultCode, data))
		}
	}

	private fun checkBarcode(intentResult: IntentResult) {
		val storedBarcode = prefs.getString(getString(R.string.pref_key_barcode), "")
		if (intentResult.contents == storedBarcode) {
			stopAlarm()
		} else {
			Toast.makeText(this, "Wrong Barcode", Toast.LENGTH_SHORT).show()
		}
	}
}
