package com.jiib.jnucenter.mvvm.repository.model.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Records(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "record_name") val record_name: String,
    @ColumnInfo(name = "record_url") val record_url: String
)
