package com.jiib.jnucenter.mvvm.repository.model.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 *   녹음 Room Database
 */

@Database(entities = arrayOf(Records::class), version = 1)
abstract class RecordsDatabase : RoomDatabase() {
    abstract fun recordsDao() : RecordsDao

    companion object {
        private var instance: RecordsDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : RecordsDatabase? {
            if (instance == null){
                synchronized(RecordsDatabase::class.java){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecordsDatabase::class.java,
                        "record-database"
                    ).build()
                }
            }
            return instance
        }
    }
}