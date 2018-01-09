package com.steven.wakealarm.settings

import android.content.Context
import android.util.AttributeSet
import com.google.zxing.integration.android.IntentIntegrator
import com.steven.wakealarm.utils.getActivity

class BarcodePreference : ClickPreference {

	constructor(context: Context)
			: super(context)

	constructor(context: Context, attrs: AttributeSet?)
			: super(context, attrs)

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
			: super(context, attrs, defStyleAttr)

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
			: super(context, attrs, defStyleAttr, defStyleRes)

	override fun onClick() {
		IntentIntegrator(getActivity(context)).initiateScan()
	}
}
