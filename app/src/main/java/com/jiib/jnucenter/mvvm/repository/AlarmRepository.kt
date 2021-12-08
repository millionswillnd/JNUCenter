package com.jiib.jnucenter.mvvm.repository

import androidx.lifecycle.MutableLiveData
import com.jiib.jnucenter.mvvm.repository.network.retrofit.alarm.AlarmDTO
import com.jiib.jnucenter.mvvm.repository.network.retrofit.alarm.AlarmService
import com.jiib.jnucenter.mvvm.repository.network.retrofit.lecture.LectureDTO
import com.jiib.jnucenter.mvvm.repository.network.retrofit.lecture.LectureService
import retrofit2.Call

class AlarmRepository {

    val alarm_service = AlarmService()

    // 강의 기한, 장학 공지, 학식 알람 세팅 변수
    val alarm_list: MutableLiveData<AlarmDTO> = MutableLiveData()


    // 서버에 알람 세팅 정보를 보낸다
    fun setAlarmSettings(lecture_alarm: Int, scholarship_alarm: Int, food_alarm: Int){
        alarm_service.setAlarmSettings(lecture_alarm, scholarship_alarm, food_alarm)
    }

    // 서버에 저장된 나의 알람 설정 정보를 받아와 LiveData에 set
    fun getAlarmSettings(){
        var alarms : AlarmDTO? = null
        val response : Call<AlarmDTO> = alarm_service.getAlarmSettings()
        alarms = response.execute().body()
        alarm_list.postValue(alarms)
    }

    // 서버로 fcm 토큰을 보낸다
    fun postFcmToken(token: String){
        alarm_service.setFcmToken(token)
    }
}