package com.example.jnucenter.mvvm.repository.network.retrofit.lecture

import retrofit2.Call
import retrofit2.http.GET

interface LectureApi {

    @GET("/lecture/date")
    fun requestLectureDate(): Call<List<LectureDTO>>
}