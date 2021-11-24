package com.example.jnucenter.mvvm.repository.network.retrofit.numbers

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NumberApi {

    // 학내 모든 전화번호 리퀘스트
    @GET("/numbers")
    fun requestNumbers(
        @Query("page_index") page_index : Int
    ) : Call<List<NumbersDTO>>
}