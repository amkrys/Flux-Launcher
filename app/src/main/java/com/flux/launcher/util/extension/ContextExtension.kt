package com.flux.launcher.util.extension

import android.content.Context

fun Context.openStatusBar() {
    val service = getSystemService("statusbar")
    val statusBarManager = Class.forName("android.app.StatusBarManager")
    statusBarManager.getMethod("expandNotificationsPanel").invoke(service)
}