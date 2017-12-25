package com.steven.wakealarm

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import android.support.annotation.RequiresApi
import com.steven.wakealarm.utils.NOTIFICATION_CHANNEL_ID
import com.steven.wakealarm.utils.PREFS_VERSION_CODE
import com.steven.wakealarm.utils.is26OrLater

class AlarmApp : Application() {

	override fun onCreate() {
		super.onCreate()
		val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
		val versionCode = sharedPreferences.getInt(PREFS_VERSION_CODE, BuildConfig.VERSION_CODE)

		if (versionCode != BuildConfig.VERSION_CODE) {
			onUpdate()
		}
		sharedPreferences.edit().putInt(PREFS_VERSION_CODE, BuildConfig.VERSION_CODE).apply()
		if (is26OrLater()) {
			createNotificationChannels()
		}
	}

	private fun onUpdate() {
	}

	@RequiresApi(Build.VERSION_CODES.O)
	private fun createNotificationChannels() {
		val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		notificationManager.createNotificationChannel(
				NotificationChannel(NOTIFICATION_CHANNEL_ID,
						getString(R.string.channel_name),
						NotificationManager.IMPORTANCE_HIGH)
		)
	}
}
