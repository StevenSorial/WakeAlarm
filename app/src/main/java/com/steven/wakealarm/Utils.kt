@file:JvmName("Utils")

package com.steven.wakealarm

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.BatteryManager
import android.os.Build
import android.telephony.TelephonyManager
import java.util.*

const val PREFS_HOURS = "hours"
const val PREFS_MINUTES = "minutes"
const val PREFS_AM_PM = "am_pm"
const val PREFS_ENABLED = "enabled"

fun isDeviceCharging(context: Context): Boolean {
	val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
	val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
	return status == BatteryManager.BATTERY_STATUS_CHARGING
			|| status == BatteryManager.BATTERY_STATUS_FULL
}

fun formatTimeLeft(millis: Long): String {
	val hours = millis / (1000 * 60 * 60)
	val minutes = millis / (1000 * 60) % 60
	if (hours == 0L) return "$minutes Minutes Left"
	else return "$hours Hours and $minutes Minutes Left"
}

fun is17OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1

fun is19OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

fun is21OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

fun is23OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

fun is24OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

fun is26OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

fun getScheduledCalendar(hour: Int, minute: Int): Calendar {
	val calendar = Calendar.getInstance()
	calendar.set(Calendar.HOUR_OF_DAY, hour)
	calendar.set(Calendar.MINUTE, minute)
	calendar.set(Calendar.SECOND, 0)
	calendar.set(Calendar.MILLISECOND, 0)
	if (System.currentTimeMillis() > calendar.timeInMillis) {
		calendar.add(Calendar.DATE, 1)
	}
	return calendar
}

fun scheduleAlarm(context: Context, hour: Int, minute: Int) {
	val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
	val cal = getScheduledCalendar(hour, minute)
}

fun MediaPlayer.setVolume(volume: Float) = setVolume(volume, volume)

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
