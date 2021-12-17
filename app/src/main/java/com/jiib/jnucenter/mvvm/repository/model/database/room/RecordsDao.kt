package com.jiib.jnucenter.mvvm.repository.model.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface RecordsDao {

    @Query("SELECT * FROM Records")
    fun getAllRecords() : LiveData<List<Records>>

    @Query("INSERT INTO Records VALUES(:id, :record_title, :record_time, :record_url)")
    fun insertRecord(id:Int?,record_title: String?, record_time: String?, record_url: String?)
}