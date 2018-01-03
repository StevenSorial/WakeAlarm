package com.steven.wakealarm

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import com.steven.wakealarm.base.BaseActivity
import com.steven.wakealarm.utils.KEY_NFC_ID
import com.steven.wakealarm.utils.byteArrayToAddress

class NFCActivity : BaseActivity() {

	private val nfcAdapter: NfcAdapter? by lazy { NfcAdapter.getDefaultAdapter(this) }

	private val pendingIntent by lazy {
		PendingIntent.getActivity(this,
				0,
				Intent(this, javaClass),
				0)
	}
	private val filters by lazy { arrayOf(IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_nfc)
	}

	override fun onResume() {
		super.onResume()
		nfcAdapter?.enableForegroundDispatch(this, pendingIntent, filters, null)
	}

	override fun onPause() {
		super.onPause()
		nfcAdapter?.disableForegroundDispatch(this)
	}

	override fun onNewIntent(intent: Intent?) {
		super.onNewIntent(intent)
		when (intent?.action) {
			NfcAdapter.ACTION_TAG_DISCOVERED -> handleNFCIntent(intent)
		}
	}

	private fun handleNFCIntent(intent: Intent) {
		val tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG) as Tag?
		val id = tag?.id
		if (id?.isNotEmpty() == true) {
			val address = byteArrayToAddress(id)
			setResult(Activity.RESULT_OK, Intent().putExtra(KEY_NFC_ID, address))
			finish()
		} else {
			setResult(Activity.RESULT_CANCELED)
			finish()
		}
	}
}
