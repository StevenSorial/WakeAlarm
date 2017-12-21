@file:JvmName("Extensions")

package com.steven.wakealarm.utils

import android.media.MediaPlayer
import android.support.v7.widget.TooltipCompat
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup

fun MediaPlayer.setVolume(volume: Float) = setVolume(volume, volume)

var RadioGroup.checkedRadioIndex: Int
	set(value) {
		(0 until childCount).filter { i -> getChildAt(i) is RadioButton }
				.map { i -> getChildAt(i) as RadioButton }
				.getOrNull(value)?.isChecked = true
	}
	get() = (0 until childCount).filter { i -> getChildAt(i) is RadioButton }
			.map { i -> getChildAt(i) as RadioButton }
			.indexOfFirst { rb -> rb.isChecked }

fun View.setTooltip(tooltipText: CharSequence?) {
	TooltipCompat.setTooltipText(this, tooltipText)
}
