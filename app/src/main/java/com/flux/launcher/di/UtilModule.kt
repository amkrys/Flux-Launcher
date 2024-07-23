package com.flux.launcher.di

import android.content.Context
import com.flux.launcher.util.basic.StorageUtil
import com.flux.launcher.domain.repository.AppRepository
import com.flux.launcher.util.basic.AppListUtil
import com.flux.launcher.util.basic.BitmapUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    @Singleton
    fun bitmapUtils(): BitmapUtil {
        return BitmapUtil()
    }

    @Provides
    @Singleton
    fun storageUtils(@ApplicationContext context: Context): StorageUtil {
        return StorageUtil(context)
    }

    @Provides
    @Singleton
    fun appListUtils(
        appRepository: AppRepository,
        bitmapUtil: BitmapUtil,
        storageUtil: StorageUtil,
        @ApplicationContext context: Context
    ): AppListUtil {
        return AppListUtil(appRepository, bitmapUtil, storageUtil, context.packageManager, context)
    }

}