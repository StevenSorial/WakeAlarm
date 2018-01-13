@file:JvmName("Utils")

package com.steven.wakealarm.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.os.BatteryManager
import android.telephony.TelephonyManager
import java.util.Calendar

fun isDeviceCharging(context: Context): Boolean {
	val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
	val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
	return status == BatteryManager.BATTERY_STATUS_CHARGING
			|| status == BatteryManager.BATTERY_STATUS_FULL
}

fun formatTimeLeft(millis: Long): String {
	val hours = millis / (1000 * 60 * 60)
	val minutes = (millis / (1000 * 60)) % 60
	if (hours == 0L) return "$minutes Minutes Left"
	else return "$hours Hours and $minutes Minutes Left"
}

fun getScheduledCalendar(hour: Int, minute: Int): Calendar {
	return Calendar.getInstance().apply {
		set(Calendar.HOUR_OF_DAY, hour)
		set(Calendar.MINUTE, minute)
		set(Calendar.SECOND, 0)
		set(Calendar.MILLISECOND, 0)
		if (System.currentTimeMillis() > timeInMillis) {
			add(Calendar.DATE, 1)
		}
	}
}

/**
 * @return `true` if the device is currently in a telephone call
 */
private fun isInTelephoneCall(context: Context): Boolean {
	val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
	return tm.callState != TelephonyManager.CALL_STATE_IDLE
}

fun setStatusBarIconPreLollipop(context: Context, enabled: Boolean) {
	val alarmChanged = Intent("android.intent.action.ALARM_CHANGED")
	alarmChanged.putExtra("alarmSet", enabled)
	context.sendBroadcast(alarmChanged)
}

fun getActivity(context: Context): Activity? {
	var c = context
	while (c is ContextWrapper) {
		if (c is Activity) return c
		c = c.baseContext
	}
	return null
}

fun byteArrayToAddress(bytes: ByteArray): String {
	val address = StringBuilder()
	var sep = ""
	for (b in bytes) {
		address.append(sep).append(String.format("%02X", b))
		sep = ":"
	}
	return address.toString()
}

fun isNFCAvailable(context: Context): Boolean = NfcAdapter.getDefaultAdapter(context) != null

