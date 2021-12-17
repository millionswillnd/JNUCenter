package com.jiib.jnucenter.mvvm.feature.record

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.jiib.jnucenter.mvvm.repository.RecordRepository
import com.jiib.jnucenter.mvvm.repository.model.database.room.Records

class RecordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RecordRepository(application)

    val record_list : LiveData<List<Records>>
        get() = repository.record_list

    fun saveRecord(title:String, time:String, url:String){
        repository.saveRecord(title, time, url)
    }

    fun getAllRecords(){
        repository.getAllRecords()
    }

    fun deleteRecord(id:Int){
        repository.deleteRecord(id)
    }


    class Factory(val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RecordViewModel(application) as T
        }
    }
}