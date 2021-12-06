package com.example.jnucenter.mvvm.feature.lecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jnucenter.mvvm.repository.LectureDateRepository
import com.example.jnucenter.mvvm.repository.network.retrofit.lecture.LectureDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LectureDateViewModel : ViewModel() {

    private val lecture_repository = LectureDateRepository()

    val lecture_date_list : MutableLiveData<List<LectureDTO>>
        get() = lecture_repository.lecture_date_list


    fun getLectureDates() : List<LectureDTO>?{
        return lecture_repository.getLectureDates()
    }
}