package com.steven.wakealarm.utils

import android.os.Build

fun is15OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1

fun is16OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN

fun is17OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1

fun is18OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2

fun is19OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

fun is21OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

fun is22OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1

fun is23OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

fun is24OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

fun is25OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1

fun is26OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

fun is27OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1
