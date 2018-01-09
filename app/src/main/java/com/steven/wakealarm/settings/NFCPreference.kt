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

class NFCPreference : ClickPreference {
	constructor(context: Context)
			: super(context)

	constructor(context: Context, attrs: AttributeSet?)
			: super(context, attrs)

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
			: super(context, attrs, defStyleAttr)

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
			: super(context, attrs, defStyleAttr, defStyleRes)

	override fun onClick() {
		val activity = getActivity(context)
		val intent = Intent(activity, NFCActivity::class.java)
		activity?.startActivityForResult(intent, NFC_REQUEST_CODE)
	}
}
