package com.flux.launcher.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flux.launcher.data.local.entitiy.AppEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppsDao {

    @Query("SELECT * from apps WHERE isHidden =:isHidden  ORDER BY appName COLLATE NOCASE ASC")
    fun getVisibleApps(isHidden: Boolean): Flow<MutableList<AppEntity>>

    @Query("SELECT * from apps ORDER BY appName COLLATE NOCASE ASC")
    fun getAllAppsFlow(): Flow<MutableList<AppEntity>>

    @Query("SELECT * FROM apps ORDER BY appName COLLATE NOCASE ASC")
    fun getAllAppsLiveData(): MutableList<AppEntity>

    @Query("SELECT * from apps ORDER BY appName COLLATE NOCASE ASC")
    fun getRecentApps(): Flow<MutableList<AppEntity>>

    @Query("SELECT * from apps WHERE isHidden =:isHidden ORDER BY appName COLLATE NOCASE ASC")
    fun getOnlyHiddenApps(isHidden: Boolean): Flow<MutableList<AppEntity>>

    @Query("SELECT COUNT(*) from apps")
    fun isAppsInDB(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(apps: List<AppEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appEntity: AppEntity)

    @Query("UPDATE apps SET isHidden = :isHidden where id IN(:ids)")
    suspend fun hideApps(ids: IntArray, isHidden: Boolean)

    @Query("UPDATE apps SET isHidden = :isHidden where id IN(:ids)")
    suspend fun unhiddenApps(ids: IntArray, isHidden: Boolean)

    @Query("DELETE FROM apps")
    suspend fun deleteAllApps()

    @Query("DELETE FROM apps where packageName = :packageName")
    suspend fun deleteApp(packageName: String)
}