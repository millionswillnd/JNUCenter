package com.jiib.jnucenter.mvvm.repository.network.retrofit.lecture

import retrofit2.Call
import retrofit2.http.GET

/**
 *   강의기한 Retrofit Api
 */

interface LectureApi {

    // 강의, 과제, 퀴즈 기한들 리퀘스트
    @GET("/lecture/date")
    fun requestLectureDate(): Call<List<LectureDTO>>
}