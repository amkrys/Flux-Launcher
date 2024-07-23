package com.flux.launcher.util.basic

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.WorkerThread
import com.flux.launcher.util.extension.getIconFolderPath
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class StorageUtil(private val context: Context) {

    @WorkerThread
    fun saveBitmapToFile(bitmap: Bitmap, label: String) {
        try {
            FileOutputStream(File(context.getIconFolderPath(label))).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}