package com.jiib.jnucenter.mvvm.repository.network.retrofit.lecture

import com.jiib.jnucenter.mvvm.repository.network.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Retrofit

/**
 *   Lecture Retrofit Service
 */

class LectureService {

    private val retrofit_client = RetrofitClient

    suspend fun getLectureDate() : Call<List<LectureDTO>> {
        val client : Retrofit = retrofit_client.getInstance()
        var lectures : List<LectureDTO>? = null
        val response : Call<List<LectureDTO>> = client.create(LectureApi::class.java).requestLectureDate()

        return response
    }
}