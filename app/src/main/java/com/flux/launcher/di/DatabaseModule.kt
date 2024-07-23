package com.flux.launcher.di

import android.app.Application
import androidx.room.Room
import com.flux.launcher.data.local.core.AppDatabase
import com.flux.launcher.data.local.repository.AppRepositoryImpl
import com.flux.launcher.domain.repository.AppRepository
import com.flux.launcher.util.constant.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, Constants.DATABASE_NAME)
            .addMigrations().build()
    }

    @Provides
    @Singleton
    fun provideAppRepository(database: AppDatabase): AppRepository {
        return AppRepositoryImpl(database.appsDao())
    }

}