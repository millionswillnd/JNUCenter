package com.jiib.jnucenter.mvvm.feature.lecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiib.jnucenter.mvvm.repository.LectureDateRepository
import com.jiib.jnucenter.mvvm.repository.network.retrofit.lecture.LectureDTO

class LectureDateViewModel : ViewModel() {

    private val lecture_repository = LectureDateRepository()

    val lecture_date_list : MutableLiveData<List<LectureDTO>>
        get() = lecture_repository.lecture_date_list

    fun getLectureDates() : List<LectureDTO>?{
        return lecture_repository.getLectureDates()
    }
}