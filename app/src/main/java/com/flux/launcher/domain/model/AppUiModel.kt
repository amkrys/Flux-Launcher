package com.flux.launcher.domain.model

data class AppUiModel(
    val id: Int? = null,
    val isHeader: Boolean = false,
    val appIcon: String = "",
    val packageName: String = "",
    val appName: String = ""
)