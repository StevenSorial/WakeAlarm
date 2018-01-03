package com.steven.wakealarm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.steven.wakealarm.base.BaseActivity
import com.steven.wakealarm.utils.KEY_NFC_ID
import com.steven.wakealarm.utils.NFC_REQUEST_CODE
import com.steven.wakealarm.utils.PREFS_CHALLENGE
import com.steven.wakealarm.utils.PREFS_ENABLED
import com.steven.wakealarm.utils.is27OrLater
import kotlinx.android.synthetic.main.activity_alarm.*

class AlarmActivity : BaseActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_alarm)
		if (is27OrLater()) {
			setTurnScreenOn(true)
			setShowWhenLocked(true)
		} else {
			window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
			window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
		}
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
		prefs.edit()?.putBoolean(PREFS_ENABLED, false)?.apply()
		btn_dismiss.setOnClickListener {
			val challenge = prefs.getInt(PREFS_CHALLENGE, 0)
			when (challenge) {
				0 -> stopAlarm()
				1 -> scanBarcode()
				2 -> scanNFC()
			}
		}
	}

	private fun stopAlarm() {
		val intent = Intent(this, AlarmService::class.java)
		stopService(intent)
		finish()
	}

	private fun scanBarcode() {
		IntentIntegrator(this).initiateScan()
	}

	private fun scanNFC() {
		val intent = Intent(this, NFCActivity::class.java)
		startActivityForResult(intent, NFC_REQUEST_CODE)
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		when (requestCode) {
			IntentIntegrator.REQUEST_CODE -> checkBarcode(resultCode, data)
			NFC_REQUEST_CODE -> checkNFC(resultCode, data)
		}
	}

	private fun checkBarcode(resultCode: Int, data: Intent?) {
		val intentResult = IntentIntegrator.parseActivityResult(IntentIntegrator.REQUEST_CODE, resultCode, data)
		val storedBarcode = prefs.getString(getString(R.string.pref_key_barcode), "")
		if (intentResult?.contents == storedBarcode) {
			stopAlarm()
		} else {
			Toast.makeText(this, "Wrong Barcode", Toast.LENGTH_SHORT).show()
		}
	}

	private fun checkNFC(resultCode: Int, data: Intent?) {
		val storedNFC = prefs.getString(getString(R.string.pref_key_nfc), "")
		val resultNFC = data?.getStringExtra(KEY_NFC_ID)
		if (resultCode == Activity.RESULT_OK && resultNFC == storedNFC) {
			stopAlarm()
		} else {
			Toast.makeText(this, "Wrong NFC", Toast.LENGTH_SHORT).show()
		}
	}
}
