package com.jiib.jnucenter.mvvm.repository.network.retrofit.alarm

import retrofit2.Call
import retrofit2.http.*

/**
 *   알람 설정 정보 관련 Retrofit Api
 */

interface AlarmApi {

    // 알람 세팅 정보를 서버의 DB 테이블에 저장
    // 0 예 1 아니오
    @PUT("/alarm/setting/{user_id}")
    fun putAlarmSettings(
        @Path("user_id") user_id : Int,
        @Query("lecture_alarm_setting") lecture_alarm_setting: Int,
        @Query("scholarship_alarm_setting") scholarship_alarm_setting: Int,
        @Query("food_alarm_setting") food_alarm_setting: Int
    ) : Call<String>

    // 알람 세팅 정보를 받아온다
    @GET("/alarm/infos/{user_id}")
    fun requestAlarmSettingInfos(
        @Path("user_id") user_id: Int
    ) : Call<AlarmDTO>

    // FCM 토큰을 서버로 전송
    @POST("/alarm/token/{user_id}")
    fun postFcmToken(
        @Path("user_id") user_id : Int,
        @Query("fcm_token") fcm_token : String
    ) : Call<String>
}