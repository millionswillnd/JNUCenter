package com.jiib.jnucenter.mvvm.feature.alarm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiib.jnucenter.mvvm.repository.AlarmRepository
import com.jiib.jnucenter.mvvm.repository.network.retrofit.alarm.AlarmDTO

/**
 *    알람 뷰모델
 */
class AlarmViewModel : ViewModel() {

    private val alarm_repository = AlarmRepository()

    // 알람 설정 여부들
    val alarm_list: MutableLiveData<AlarmDTO>
        get() = alarm_repository.alarm_list

    // 알람 설정 정보를 서버로 전송
    fun setAlarmSettings(lecture_alarm: Int, scholarship_alarm: Int, food_alarm: Int){
        alarm_repository.setAlarmSettings(lecture_alarm, scholarship_alarm, food_alarm)
    }

    // 알람 설정 정보를 받아온다
    fun getAlarmSettings(){
        alarm_repository.getAlarmSettings()
    }

    // fcm 토큰을 서버로 전송
    fun sendFcmToken(token : String){
        alarm_repository.postFcmToken(token)
    }

}