package com.example.jnucenter.mvvm.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.jnucenter.mvvm.repository.network.retrofit.lecture.LectureDTO
import com.example.jnucenter.mvvm.repository.network.retrofit.lecture.LectureService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LectureDateRepository {

    val lecture_service = LectureService()

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