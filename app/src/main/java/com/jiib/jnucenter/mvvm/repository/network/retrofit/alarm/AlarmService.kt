package com.jiib.jnucenter.mvvm.repository.network.retrofit.alarm

import com.jiib.jnucenter.mvvm.repository.network.retrofit.RetrofitClient
import com.jiib.jnucenter.mvvm.repository.network.retrofit.lecture.LectureApi
import com.jiib.jnucenter.mvvm.repository.network.retrofit.lecture.LectureDTO
import retrofit2.Call
import retrofit2.Retrofit

// 추후 다중이용자 서비스로 개선 시 user_id를 제대로 값을 주도록 한다
class AlarmService {

    private val retrofit_client = RetrofitClient

    fun setAlarmSettings(lecture_alarm: Int, scholarship_alarm: Int, food_alarm: Int) : Call<String>{
        val client : Retrofit = retrofit_client.getInstance()
        val response = client.create(AlarmApi::class.java).putAlarmSettings(1, lecture_alarm, scholarship_alarm, food_alarm)

        return response
    }

    fun getAlarmSettings() : Call<AlarmDTO> {
        val client : Retrofit = retrofit_client.getInstance()
        val response : Call<AlarmDTO> = client.create(AlarmApi::class.java).requestAlarmSettingInfos(1)

        return response
    }

    fun setFcmToken(token : String){
        val client : Retrofit = retrofit_client.getInstance()
        val response = client.create(AlarmApi::class.java).postFcmToken(token)

    return response
    }
}