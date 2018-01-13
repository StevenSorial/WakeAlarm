package com.steven.wakealarm.settings

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import com.steven.wakealarm.NFCActivity
import com.steven.wakealarm.R
import com.steven.wakealarm.utils.NFC_REQUEST_CODE
import com.steven.wakealarm.utils.getActivity
import com.steven.wakealarm.utils.isNFCAvailable

class NFCPreference : ClickPreference {

	private val nfcAvailable by lazy { isNFCAvailable(context) }

	constructor(context: Context)
			: super(context)

	constructor(context: Context, attrs: AttributeSet?)
			: super(context, attrs)

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
			: super(context, attrs, defStyleAttr)

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
			: super(context, attrs, defStyleAttr, defStyleRes)

	init {
		isEnabled = nfcAvailable
	}

	override fun getSummary(): CharSequence? {
		if (!nfcAvailable) return context.getString(R.string.pref_summary_nfc_not_available)
		return super.getSummary()
	}

	override fun onClick() {
		val activity = getActivity(context)
		val intent = Intent(activity, NFCActivity::class.java)
		activity?.startActivityForResult(intent, NFC_REQUEST_CODE)
	}
}
