package com.jiib.jnucenter.mvvm.repository

import androidx.lifecycle.MutableLiveData
import com.jiib.jnucenter.mvvm.repository.network.retrofit.lecture.LectureDTO
import com.jiib.jnucenter.mvvm.repository.network.retrofit.lecture.LectureService
import retrofit2.Call

/**
 *   강의기한 관련 Repository
 */

class LectureDateRepository {

    private val lecture_service = LectureService()
    // 강의의 과제, 싸강, 퀴즈 등의 title과 기한을 담은 List
    val lecture_date_list: MutableLiveData<List<LectureDTO>> = MutableLiveData()


    // 강의의 과제, 싸강, 퀴즈 등의 title과 기한을 얻어온다
    fun getLectureDates() : List<LectureDTO>?{

        var list : List<LectureDTO>? = null
        val response : Call<List<LectureDTO>> = lecture_service.getLectureDate()
        list = response.execute().body()

        lecture_date_list.postValue(list)

        return list
    }

}