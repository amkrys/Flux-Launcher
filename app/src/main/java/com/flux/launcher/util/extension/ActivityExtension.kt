package com.flux.launcher.util.extension

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flux.launcher.BuildConfig

fun AppCompatActivity.enableWallpaperFlag() {
    window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER)
    window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
}

fun AppCompatActivity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.log(message: String?) {
    if (BuildConfig.DEBUG) message?.let { Log.e(this::class.java.simpleName, it) }
}

fun AppCompatActivity.requireContext(): AppCompatActivity {
    return this
}

inline fun <reified TargetActivity : AppCompatActivity> AppCompatActivity.openActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, TargetActivity::class.java).apply(block))
}