package com.jiib.jnucenter.mvvm.feature.lecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiib.jnucenter.mvvm.repository.LectureDateRepository
import com.jiib.jnucenter.mvvm.repository.network.retrofit.lecture.LectureDTO

class LectureDateViewModel : ViewModel() {

    private val lecture_repository = LectureDateRepository()

    // 강의 기한 리스트
    val lecture_date_list : MutableLiveData<List<LectureDTO>>
        get() = lecture_repository.lecture_date_list

    // 강의 기한을 담은 리스트 api를 리파지토리에 요청
    fun getLectureDates() : List<LectureDTO>?{
        return lecture_repository.getLectureDates()
    }
}