package com.example.jnucenter.mvvm.repository.network.retrofit.numbers

import retrofit2.Call
import retrofit2.http.GET

interface NumberApi {

    // 학내 모든 전화번호 리퀘스트
    @GET("/numbers")
    fun requestNumbers() : Call<List<NumbersDTO>>
}