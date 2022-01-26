package com.jiib.jnucenter.mvvm.repository.model.database.room

import androidx.room.Dao
import androidx.room.Query

/**
 *   녹음 관련 dao
 */

@Dao
interface RecordsDao {

    @Query("SELECT * FROM Records")
    suspend fun getAllRecords() : List<Records>

    @Query("INSERT INTO Records VALUES(:id, :record_title, :record_time, :record_url)")
    suspend fun insertRecord(id:Int?,record_title: String?, record_time: String?, record_url: String?)

    @Query("DELETE FROM Records WHERE id=:id")
    suspend fun deleteRecord(id:Int)

    @Query("SELECT record_url FROM Records WHERE id=:id")
    suspend fun getRecordPath(id: Int) : String
}