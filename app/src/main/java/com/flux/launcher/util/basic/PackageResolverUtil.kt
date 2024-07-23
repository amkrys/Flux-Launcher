package com.flux.launcher.util.basic

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import androidx.annotation.WorkerThread
import com.flux.launcher.util.extension.toLowerCased

object PackageResolverUtil {

    @WorkerThread
    fun getSortedAppList(
        packageManager: PackageManager
    ): MutableList<AppInfo> {

        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val list = packageManager.queryIntentActivities(
            mainIntent,
            PackageManager.GET_META_DATA
        )
        
        val appInoList: MutableList<AppInfo> = ArrayList(list.size)

        list.forEach { resolveInfo ->
            val packageInfo = AppInfo(
                icon = resolveInfo.loadIcon(packageManager),
                packageName = resolveInfo.activityInfo.packageName,
                label = resolveInfo.loadLabel(packageManager).toString()
            )
            appInoList.add(packageInfo)
        }

        appInoList.sortBy {
            it.label.toLowerCased()
        }

        return appInoList
    }

    @WorkerThread
    fun getAppInfoFromPackageName(packageManager: PackageManager, packageName: String): AppInfo? {
        val intent = Intent()
        intent.`package` = packageName
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        var appInfo: AppInfo? = null
        packageManager.resolveActivity(intent, 0)?.let {
            appInfo = AppInfo(
                icon = it.loadIcon(packageManager),
                packageName = it.activityInfo.packageName,
                label = it.loadLabel(packageManager).toString()
            )
        }
        return appInfo
    }

    private fun isUserApp(ai: ResolveInfo): Boolean {
        val mask = ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP
        return (ai.activityInfo.flags and mask) == 0
    }

    data class AppInfo(
        val icon: Drawable,
        val packageName: String,
        val label: String
    )

}