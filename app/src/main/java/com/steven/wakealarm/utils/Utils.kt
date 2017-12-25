@file:JvmName("Utils")

package com.steven.wakealarm.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.telephony.TelephonyManager
import java.util.Calendar

const val PREFS_HOURS = "hours"
const val PREFS_MINUTES = "minutes"
const val PREFS_AM_PM = "am_pm"
const val PREFS_ENABLED = "enabled"
const val PREFS_CHALLENGE = "challenge"
//const val PREFS_BARCODE = "barcode"
const val PREFS_VERSION_CODE = "version_code"

const val NOTIFICATION_CHANNEL_ID = "main_channel"

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
//fun getActivity(context: Context): Activity? {
//	if (context is AppCompatActivity) return context
//	if (context is ContextWrapper) return getActivity(context.baseContext)
//	return null
//}
//

