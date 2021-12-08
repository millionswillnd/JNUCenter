package com.jiib.jnucenter.mvvm.repository.network.retrofit.alarm

import com.jiib.jnucenter.mvvm.repository.network.retrofit.RetrofitClient
import com.jiib.jnucenter.mvvm.repository.network.retrofit.lecture.LectureApi
import com.jiib.jnucenter.mvvm.repository.network.retrofit.lecture.LectureDTO
import retrofit2.Call
import retrofit2.Retrofit

class AlarmService {

    private val retrofit_client = RetrofitClient

    fun setAlarmSettings(lecture_alarm: Int, scholarship_alarm: Int, food_alarm: Int){
        val client : Retrofit = retrofit_client.getInstance()
        client.create(AlarmApi::class.java).requestAlarmSettings(lecture_alarm, scholarship_alarm, food_alarm)
    }

    fun getAlarmSettings() : Call<AlarmDTO> {
        val client : Retrofit = retrofit_client.getInstance()
        val response : Call<AlarmDTO> = client.create(AlarmApi::class.java).requestAlarmSettingInfos()

        return response
    }

    fun setFcmToken(token : String){
        val client : Retrofit = retrofit_client.getInstance()
        client.create(AlarmApi::class.java).postFcmToken(token)
    }
}