package com.steven.wakealarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.graphics.Color
import android.os.Bundle
import android.support.v4.graphics.drawable.DrawableCompat
import android.widget.CompoundButton
import android.widget.TimePicker
import com.steven.wakealarm.base.BaseActivity
import com.steven.wakealarm.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), OnSharedPreferenceChangeListener {

	private var alarmManager: AlarmManager? = null
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		prefs?.registerOnSharedPreferenceChangeListener(this)
		alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager?
		settingsButton?.setOnClickListener {
			startActivity(Intent(this, SettingsActivity::class.java))
		}
		DrawableCompat.setTint(settingsButton.drawable, if (theme == "dark") Color.LTGRAY else
			Color.DKGRAY)
		timePicker?.setIs24HourView(false)
		timePicker?.currentHour = prefs?.getInt(PREFS_HOURS, 0)
		timePicker?.currentMinute = prefs?.getInt(PREFS_MINUTES, 0)
		switch_alarm?.isChecked = prefs!!.getBoolean(PREFS_ENABLED, false)
		timePicker?.setOnTimeChangedListener { _: TimePicker, _: Int, _: Int ->
			switch_alarm.isChecked = true
			setRemainingTime()
			scheduleAlarm()
		}
		switch_alarm?.setOnCheckedChangeListener { _: CompoundButton?, b: Boolean ->
			if (b) scheduleAlarm() else cancelAlarm()
		}
	}

	override fun onResume() {
		super.onResume()
		if (prefs?.getBoolean(PREFS_ENABLED, false) == true) {
			setRemainingTime()
		}
	}

	override fun onPause() {
		super.onPause()
		prefs?.edit()
				?.putInt(PREFS_HOURS, timePicker.currentHour)
				?.putInt(PREFS_MINUTES, timePicker.currentMinute)
				?.putBoolean(PREFS_ENABLED, switch_alarm.isChecked)
				?.apply()
	}

	override fun onDestroy() {
		super.onDestroy()
		prefs?.unregisterOnSharedPreferenceChangeListener(this)
	}

	override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
		if (key == getString(R.string.pref_key_theme)) {
			startActivity(Intent(this, MainActivity::class.java))
			finish()
		}
	}

	private fun setRemainingTime() {
		if (!switch_alarm.isChecked) return
		val cal = getScheduledCalendar(timePicker.currentHour, timePicker.currentMinute)
		RemainingTimeTV?.text = formatTimeLeft(cal.timeInMillis - System.currentTimeMillis())
	}

	private fun scheduleAlarm() {
		val i = Intent(this, AlarmService::class.java)
		val pendingIntent = PendingIntent.getService(applicationContext, 1, i,
				0)
		val calendar = getScheduledCalendar(timePicker.currentHour, timePicker.currentMinute)
		RemainingTimeTV?.text = formatTimeLeft(calendar.timeInMillis - System.currentTimeMillis())
		if (is21OrLater()) {
			val showIntent = Intent(applicationContext, MainActivity::class.java)
			val showPendingIntent = PendingIntent.getActivity(applicationContext, 0, showIntent,
					0)
			alarmManager?.setAlarmClock(
					AlarmManager.AlarmClockInfo(calendar.timeInMillis,
							showPendingIntent), pendingIntent)
		} else if (is19OrLater()) {
			alarmManager?.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
		} else {
			alarmManager?.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
		}

	}

	private fun cancelAlarm() {
		val intent = Intent(this, AlarmService::class.java)
		val pendingIntent = PendingIntent.getService(applicationContext, 1, intent,
				0)
		alarmManager?.cancel(pendingIntent)
		RemainingTimeTV?.text = ""
	}
}
