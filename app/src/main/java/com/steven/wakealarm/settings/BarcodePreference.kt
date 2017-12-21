package com.steven.wakealarm.settings

import android.content.Context
import android.content.res.TypedArray
import android.support.v7.preference.Preference
import android.util.AttributeSet
import android.util.Log
import com.google.zxing.integration.android.IntentIntegrator
import com.steven.wakealarm.R
import com.steven.wakealarm.utils.getActivity

class BarcodePreference : Preference {

	var barcodeValue: String? = null
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
		barcodeValue = if (restorePersistedValue) getPersistedString(null) else defaultValue as String
		Log.d("BarcodePreference", "Initial Value $barcodeValue")
	}

	override fun setSummary(summary: CharSequence?) {
		super.setSummary(summary)
		this.summary = summary.toString()
		Log.d("BarcodePreference", "summary" + summary)
	}

	override fun getSummary(): CharSequence? {
		if (summary == null) return super.getSummary()
		if (barcodeValue.isNullOrBlank()) return context.getString(R.string.pref_summary_barcode_empty)
		return String.format(summary.toString(), barcodeValue)
	}

	override fun onClick() {
		super.onClick()
		IntentIntegrator(getActivity(context)).initiateScan()
	}
}
