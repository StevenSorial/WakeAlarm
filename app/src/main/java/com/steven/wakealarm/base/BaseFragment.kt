package com.steven.wakealarm.base

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {

	protected val TAG: String = javaClass.simpleName

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		Log.d(TAG, "Fragment Created")
	}

	override fun onCreateView(inflater: LayoutInflater?,
							  container: ViewGroup?, savedInstanceState: Bundle?): View? {
		Log.d(TAG, "Fragment View Created")
		return super.onCreateView(inflater, container, savedInstanceState)
	}

	override fun onPause() {
		super.onPause()
		Log.d(TAG, "Fragment Paused")
	}

	override fun onResume() {
		super.onResume()
		Log.d(TAG, "Fragment Resumed")
	}

	override fun setUserVisibleHint(isVisibleToUser: Boolean) {
		super.setUserVisibleHint(isVisibleToUser)
		Log.d(TAG, "Fragment isVisible: $isVisibleToUser")
	}

	override fun onDestroyView() {
		super.onDestroyView()
		Log.d(TAG, "Fragment View Destroyed")
	}

	override fun onDestroy() {
		super.onDestroy()
		Log.d(TAG, "Fragment Destroyed")
	}
}
