package com.flux.launcher.data.local.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flux.launcher.data.local.dao.AppsDao
import com.flux.launcher.data.local.entitiy.AppEntity

@Database(entities = [AppEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appsDao(): AppsDao
}