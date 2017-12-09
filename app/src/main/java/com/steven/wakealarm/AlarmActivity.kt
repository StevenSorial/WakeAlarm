package com.steven.wakealarm

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.steven.wakealarm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_alarm.*

class AlarmActivity : BaseActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_alarm)
		window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
		window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
		btn_dismiss.setOnClickListener {
			val intent = Intent(this, AlarmService::class.java)
			stopService(intent)
			onBackPressed()
		}
	}
}
