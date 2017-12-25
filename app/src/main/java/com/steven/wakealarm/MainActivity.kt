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
import android.widget.RadioGroup
import com.google.zxing.integration.android.IntentIntegrator
import com.steven.wakealarm.base.BaseActivity
import com.steven.wakealarm.settings.SettingsActivity
import com.steven.wakealarm.utils.PREFS_CHALLENGE
import com.steven.wakealarm.utils.PREFS_ENABLED
import com.steven.wakealarm.utils.PREFS_HOURS
import com.steven.wakealarm.utils.PREFS_MINUTES
import com.steven.wakealarm.utils.checkedRadioIndex
import com.steven.wakealarm.utils.formatTimeLeft
import com.steven.wakealarm.utils.getScheduledCalendar
import com.steven.wakealarm.utils.is19OrLater
import com.steven.wakealarm.utils.is21OrLater
import com.steven.wakealarm.utils.is26OrLater
import com.steven.wakealarm.utils.setTooltip
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), OnSharedPreferenceChangeListener {

	private lateinit var alarmManager: AlarmManager

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		prefs.registerOnSharedPreferenceChangeListener(this)
		alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
		settingsButton.setOnClickListener {
			startActivity(Intent(this, SettingsActivity::class.java))
		}
		DrawableCompat.setTint(settingsButton.drawable, if (theme == "dark") Color.LTGRAY else
			Color.DKGRAY)

		timePicker.apply {
			selectDate(getScheduledCalendar(prefs.getInt(PREFS_HOURS, 0),
					prefs.getInt(PREFS_MINUTES, 0)))
			setListener { _, _ ->
				switch_alarm.isChecked = true
				scheduleAlarm()
			}
		}
		challengeRadioGroup.apply {
			checkedRadioIndex = prefs.getInt(PREFS_CHALLENGE, 0)
			setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
				prefs.edit().putInt(PREFS_CHALLENGE, radioGroup.checkedRadioIndex).apply()
				if (radioGroup.checkedRadioIndex == 1
						&& prefs.getString(getString(R.string.pref_key_barcode), "").isEmpty()) {
					IntentIntegrator(this@MainActivity).initiateScan()
					radioGroup.checkedRadioIndex = 0
				}
			}
		}
		switch_alarm.setOnCheckedChangeListener { _: CompoundButton?, b: Boolean ->
			if (b) scheduleAlarm() else cancelAlarm()
		}

		challengeBarcodeRadio.setTooltip(getString(R.string.barcode))
		challengeNoneRadio.setTooltip(getString(R.string.none))
		settingsButton.setTooltip("Settings")
	}

	override fun onResume() {
		super.onResume()
		switch_alarm.isChecked = prefs.getBoolean(PREFS_ENABLED, false)
		setRemainingTime()
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		val barcodeResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
		if (barcodeResult != null) {
			if (barcodeResult.contents != null) {
				prefs.edit()
						.putString(getString(R.string.pref_key_barcode), barcodeResult.contents)
						.apply()
				challengeRadioGroup.checkedRadioIndex = 1
			} else {
				challengeRadioGroup.checkedRadioIndex = 0
			}
		}
	}

	override fun onPause() {
		super.onPause()
		prefs.edit()
				?.putInt(PREFS_HOURS, timePicker.date.hours)
				?.putInt(PREFS_MINUTES, timePicker.date.minutes)
				?.putBoolean(PREFS_ENABLED, switch_alarm.isChecked)
				?.apply()
	}

	override fun onDestroy() {
		super.onDestroy()
		prefs.unregisterOnSharedPreferenceChangeListener(this)
	}

	override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
		when (key) {
			getString(R.string.pref_key_theme) -> {
				startActivity(Intent(this, MainActivity::class.java))
				finish()
			}
		}
	}

	private fun setRemainingTime() {
		if (!switch_alarm.isChecked) return
		val cal = getScheduledCalendar(timePicker.date.hours, timePicker.date.minutes)
		RemainingTimeTV?.text = formatTimeLeft(cal.timeInMillis - System.currentTimeMillis())
	}

	private fun scheduleAlarm() {
		val i = Intent(this, AlarmService::class.java)
		val pendingIntent = if (is26OrLater()) {
			PendingIntent.getForegroundService(applicationContext, 1, i, 0)
		} else {
			PendingIntent.getService(applicationContext, 1, i, 0)
		}
		val calendar = getScheduledCalendar(timePicker.date.hours, timePicker.date.minutes)
		setRemainingTime()
		if (is21OrLater()) {
			val showIntent = Intent(applicationContext, MainActivity::class.java)
			val showPendingIntent = PendingIntent.getActivity(applicationContext, 0, showIntent,
					0)
			alarmManager.setAlarmClock(
					AlarmManager.AlarmClockInfo(
							calendar.timeInMillis, showPendingIntent), pendingIntent)
		} else if (is19OrLater()) {
			alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
		} else {
			alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
		}
	}

	private fun cancelAlarm() {
		val intent = Intent(this, AlarmService::class.java)
		val pendingIntent = PendingIntent.getService(applicationContext, 1, intent,
				0)
		alarmManager.cancel(pendingIntent)
		RemainingTimeTV?.text = ""
	}
}
