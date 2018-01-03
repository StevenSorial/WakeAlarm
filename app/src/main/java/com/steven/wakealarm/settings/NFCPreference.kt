package com.steven.wakealarm.settings

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.support.v7.preference.Preference
import android.util.AttributeSet
import android.util.Log
import com.steven.wakealarm.NFCActivity
import com.steven.wakealarm.R
import com.steven.wakealarm.utils.NFC_REQUEST_CODE
import com.steven.wakealarm.utils.getActivity

class NFCPreference : Preference {

	var nfcValue: String? = null
	private var summary: String?

	constructor(context: Context)
			: super(context)

	constructor(context: Context, attrs: AttributeSet?)
			: super(context, attrs)

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
			: super(context, attrs, defStyleAttr)

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
			: super(context, attrs, defStyleAttr, defStyleRes)

	init {
		summary = super.getSummary()?.toString()
	}

	override fun onGetDefaultValue(a: TypedArray, index: Int): Any = a.getString(index)

	override fun onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any?) {
		super.onSetInitialValue(restorePersistedValue, defaultValue)
		nfcValue = if (restorePersistedValue) getPersistedString(null) else defaultValue as String
	}

	override fun setSummary(summary: CharSequence?) {
		super.setSummary(summary)
		this.summary = summary.toString()
	}

	override fun getSummary(): CharSequence? {
		if (summary == null) return super.getSummary()
		if (nfcValue.isNullOrBlank()) return context.getString(R.string.pref_summary_nfc_empty)
		return String.format(summary.toString(), nfcValue)
	}

	override fun onClick() {
		super.onClick()
		val activity = getActivity(context)
		val intent = Intent(activity, NFCActivity::class.java)
		activity?.startActivityForResult(intent, NFC_REQUEST_CODE)
	}
}
