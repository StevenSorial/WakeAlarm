package com.steven.wakealarm.settings

import android.content.Context
import android.content.res.TypedArray
import android.support.v7.preference.Preference
import android.util.AttributeSet
import com.steven.wakealarm.R

abstract class ClickPreference : Preference {

	var value: String? = null
	private var xmlSummary: String? = super.getSummary()?.toString()

	constructor(context: Context)
			: super(context)

	constructor(context: Context, attrs: AttributeSet?)
			: super(context, attrs)

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
			: super(context, attrs, defStyleAttr)

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
			: super(context, attrs, defStyleAttr, defStyleRes)

	override fun onGetDefaultValue(a: TypedArray, index: Int): Any = a.getString(index)

	override fun onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any?) {
		super.onSetInitialValue(restorePersistedValue, defaultValue)
		value = if (restorePersistedValue) getPersistedString(null) else defaultValue as String
	}

	override fun getSummary(): CharSequence? {
		if (xmlSummary == null) return null
		if (value.isNullOrBlank()) return context.getString(R.string.pref_summary_empty)
		return String.format(xmlSummary.toString(), value)
	}

	abstract override fun onClick()
}
