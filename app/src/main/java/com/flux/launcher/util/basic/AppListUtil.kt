package com.flux.launcher.util.basic

import android.content.Context
import android.content.pm.PackageManager
import android.icu.util.Calendar
import androidx.annotation.WorkerThread
import com.flux.launcher.data.local.entitiy.AppEntity
import com.flux.launcher.domain.repository.AppRepository
import com.flux.launcher.util.extension.getIconFolderPath
import javax.inject.Inject

class AppListUtil @Inject constructor(
    private val appRepository: AppRepository,
    private val bitmapUtil: BitmapUtil,
    private val storageUtil: StorageUtil,
    private val packageManager: PackageManager,
    private val context: Context
) {

    @WorkerThread
    suspend fun saveAppsInDB() {
        if (appRepository.isAppsInDB() <= 0) {
            PackageResolverUtil.getSortedAppList(packageManager).forEach { appInfo ->
                    bitmapUtil.drawableToBitmap(appInfo.icon)?.let {
                        if (appInfo.packageName != context.packageName) {
                            storageUtil.saveBitmapToFile(it, appInfo.label)
                            appRepository.insert(
                                AppEntity(
                                    packageName = appInfo.packageName,
                                    appIcon = context.getIconFolderPath(appInfo.label),
                                    appName = appInfo.label,
                                    favorite = false,
                                    isHidden = false,
                                    createTime = Calendar.getInstance().timeInMillis.toString()
                                )
                            )
                        }
                    }
                }
        }
    }

    @WorkerThread
    suspend fun removeAppFromDB(packageName: String) {
        appRepository.deleteApp(packageName)
    }

    @WorkerThread
    suspend fun addAppToDB(packageName: String) {
        PackageResolverUtil.getAppInfoFromPackageName(packageManager, packageName)?.let { appInfo ->
                bitmapUtil.drawableToBitmap(appInfo.icon)?.let {
                    storageUtil.saveBitmapToFile(it, appInfo.label)
                    appRepository.insert(
                        AppEntity(
                            packageName = appInfo.packageName,
                            appIcon = context.getIconFolderPath(appInfo.label),
                            appName = appInfo.label,
                            favorite = false,
                            isHidden = false,
                            createTime = Calendar.getInstance().timeInMillis.toString()
                        )
                    )
                }
            }
    }

}