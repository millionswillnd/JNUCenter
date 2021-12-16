package com.jiib.jnucenter.mvvm.repository.model.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface RecordsDao {

    @Query("SELECT * FROM Records")
    fun getAllRecords() : LiveData<List<Records>>

}