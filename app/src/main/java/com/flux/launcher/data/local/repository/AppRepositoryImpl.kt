package com.flux.launcher.data.local.repository

import androidx.annotation.WorkerThread
import com.flux.launcher.data.local.dao.AppsDao
import com.flux.launcher.data.local.entitiy.AppEntity
import com.flux.launcher.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val appsDao: AppsDao
): AppRepository {

    override suspend fun getRecentApps(includeHidden: Boolean): Flow<MutableList<AppEntity>> {
        return appsDao.getAllAppsFlow()
    }

    override suspend fun getApps(includeHidden: Boolean): Flow<MutableList<AppEntity>> {
        return if (includeHidden) {
            appsDao.getAllAppsFlow()
        } else {
            appsDao.getVisibleApps(false)
        }
    }

    override suspend fun getOnlyHiddenApps(): Flow<MutableList<AppEntity>> {
        return appsDao.getOnlyHiddenApps(true)
    }

    override suspend fun isAppsInDB(): Int {
        return appsDao.isAppsInDB()
    }

    @WorkerThread
    override suspend fun deleteAllApps() {
        return appsDao.deleteAllApps()
    }

    @WorkerThread
    override suspend fun deleteApp(packageName: String) {
        return appsDao.deleteApp(packageName)
    }

    @WorkerThread
    override suspend fun insertAll(apps: List<AppEntity>) {
        appsDao.insertAll(apps)
    }

    @WorkerThread
    override suspend fun insert(appEntity: AppEntity) {
        appsDao.insert(appEntity)
    }

    @WorkerThread
    override suspend fun hideApps(ids: IntArray) {
        appsDao.hideApps(ids, true)
    }

    @WorkerThread
    override suspend fun unhiddenApps(ids: IntArray) {
        appsDao.unhiddenApps(ids, false)
    }

}