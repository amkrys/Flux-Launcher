package com.flux.launcher.domain.model

import android.graphics.drawable.Drawable

data class FilterUiModel(
    val icon: Drawable? = null,
    val label: String = "",
    var isSelected: Boolean = false
)