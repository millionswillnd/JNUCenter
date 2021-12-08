package com.jiib.jnucenter.mvvm.repository.network.retrofit.alarm

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AlarmApi {

    // 알람 세팅 정보를 서버의 DB 테이블에 저장
    // 모든 값은 예 아니오기 때문에 0,1로 표시한다.
    // 0 예 1 아니오
    @POST("/alarm/setting")
    fun requestAlarmSettings(
        @Query("lecture_alarm_setting") lecture_alarm_setting: Int,
        @Query("scholarship_alarm_setting") scholarship_alarm_setting: Int,
        @Query("food_alarm_setting") food_alarm_setting: Int
    )

    // 알람 세팅 정보를 받아온다
    @GET("/alarm/infos")
    fun requestAlarmSettingInfos() : Call<AlarmDTO>

    // FCM 토큰을 서버로 전송
    @POST("/alarm/token")
    fun postFcmToken(
        @Query("fcm_token") fcm_token : String
    )


}