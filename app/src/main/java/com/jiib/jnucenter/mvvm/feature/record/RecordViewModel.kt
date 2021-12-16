package com.jiib.jnucenter.mvvm.feature.record

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.jiib.jnucenter.mvvm.repository.RecordRepository

class RecordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RecordRepository(application)


}