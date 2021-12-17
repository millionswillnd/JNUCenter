package com.jiib.jnucenter.mvvm.repository.model.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Records(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    @ColumnInfo(name = "record_title") val record_title: String,
    @ColumnInfo(name = "record_time") val record_time : String,
    @ColumnInfo(name = "record_url") val record_url: String
)
