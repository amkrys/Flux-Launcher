package com.flux.launcher.domain.mapper

import com.flux.launcher.domain.model.AppUiModel
import com.flux.launcher.data.local.entitiy.AppEntity

fun AppEntity.toUiModel() = AppUiModel(
    id = id, packageName = packageName, appIcon = appIcon, appName = appName
)