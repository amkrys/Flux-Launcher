package com.flux.launcher.util.extension

import android.content.Context
import com.flux.launcher.util.constant.Constants
import java.io.File

fun Context.openStatusBar() {
    val service = getSystemService("statusbar")
    val statusBarManager = Class.forName("android.app.StatusBarManager")
    statusBarManager.getMethod("expandNotificationsPanel").invoke(service)
}

fun Context.getIconFolderPath(label: String): String {
    return "${applicationContext.getExternalFilesDir(Constants.ICON_FOLDER_NAME)}${File.separator}${label.removeSpecialChars()}"
}