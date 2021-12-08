package com.jiib.jnucenter.mvvm.repository.network.retrofit.alarm

import retrofit2.Call
import retrofit2.http.*

interface AlarmApi {

    // 알람 세팅 정보를 서버의 DB 테이블에 저장
    // 모든 값은 예 아니오기 때문에 0,1로 표시한다.
    // 0 예 1 아니오
    @PUT("/alarm/setting/{user_id}")
    fun putAlarmSettings(
        @Path("user_id") user_id : Int,    // 일단 다중 서비스가 아니기에 미리 생성한 본인의 id만 입력. 차후 서비스 개선시 수정
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
    @POST("/alarm/token")
    fun postFcmToken(
        @Query("fcm_token") fcm_token : String
    )


}