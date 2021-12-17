package com.jiib.jnucenter.mvvm.feature.record

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jiib.jnucenter.mvvm.repository.RecordRepository

class RecordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RecordRepository(application)


    fun saveRecord(title:String, time:String, url:String){
        repository.saveRecord(title, time, url)
    }

    class Factory(val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RecordViewModel(application) as T
        }
    }
}