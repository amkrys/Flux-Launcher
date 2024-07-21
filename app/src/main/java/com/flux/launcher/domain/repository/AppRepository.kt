package com.flux.launcher.domain.repository

import com.flux.launcher.data.local.entitiy.AppEntity
import kotlinx.coroutines.flow.Flow


interface AppRepository {

    suspend fun getRecentApps(includeHidden: Boolean = false): Flow<MutableList<AppEntity>>

    suspend fun getApps(includeHidden: Boolean = false): Flow<MutableList<AppEntity>>

    suspend fun getOnlyHiddenApps(): Flow<MutableList<AppEntity>>

    suspend fun isAppsInDB(): Int

    suspend fun deleteAllApps()

    suspend fun deleteApp(packageName: String)

    suspend fun insertAll(apps: List<AppEntity>)

    suspend fun insert(appEntity: AppEntity)

    suspend fun hideApps(ids: IntArray)

    suspend fun unhiddenApps(ids: IntArray)

}