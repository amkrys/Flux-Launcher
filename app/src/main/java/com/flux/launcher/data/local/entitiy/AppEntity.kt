package com.flux.launcher.data.local.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apps")
data class AppEntity(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val packageName: String,
    val appIcon: String,
    val appName: String,
    val favorite: Boolean,
    val createTime: String,
    var isSelected: Boolean = false,
    @ColumnInfo(name = "isHidden", index = true) var isHidden: Boolean = false

)