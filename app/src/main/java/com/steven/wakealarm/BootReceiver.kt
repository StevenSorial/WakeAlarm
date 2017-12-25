package com.steven.wakealarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import com.steven.wakealarm.utils.PREFS_ENABLED
import com.steven.wakealarm.utils.PREFS_HOURS
import com.steven.wakealarm.utils.PREFS_MINUTES
import com.steven.wakealarm.utils.getScheduledCalendar
import com.steven.wakealarm.utils.is19OrLater
import com.steven.wakealarm.utils.is21OrLater
import com.steven.wakealarm.utils.is24OrLater
import com.steven.wakealarm.utils.is26OrLater

class BootReceiver : BroadcastReceiver() {

	private val TAG: String = BootReceiver::class.java.simpleName

	override fun onReceive(context: Context?, intent: Intent?) {
		val bootIntent = if (is24OrLater()) Intent.ACTION_LOCKED_BOOT_COMPLETED else
			Intent.ACTION_BOOT_COMPLETED
		if (intent?.action != bootIntent) return

		Log.d(TAG, "Broadcast Received")
		val prefs = PreferenceManager.getDefaultSharedPreferences(context)
		val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

		if (!prefs.getBoolean(PREFS_ENABLED, false)) return
		val calendar = getScheduledCalendar(prefs.getInt(PREFS_HOURS, 0),
				prefs.getInt(PREFS_MINUTES, 0))
		val i = Intent(context?.applicationContext, AlarmService::class.java)

		val pendingIntent = if (is26OrLater()) {
			PendingIntent.getForegroundService(context?.applicationContext, 1, i, 0)
		} else {
			PendingIntent.getService(context?.applicationContext, 1, i, 0)
		}

		if (is21OrLater()) {
			val showIntent = Intent(context?.applicationContext, MainActivity::class.java)
			val showPendingIntent = PendingIntent.getActivity(context?.applicationContext, 0,
					showIntent, 0)
			alarmManager?.setAlarmClock(AlarmManager.AlarmClockInfo(calendar
					.timeInMillis, showPendingIntent), pendingIntent)
		} else if (is19OrLater()) {
			alarmManager?.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
					pendingIntent)
		} else {
			alarmManager?.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
		}
	}
}
