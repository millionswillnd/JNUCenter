package com.jiib.jnucenter.mvvm.repository.network.retrofit.numbers

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *   전화번호부 Retrofit Api
 */

interface NumberApi {

    // 학내 모든 전화번호 리퀘스트, 페이징 적용 o
    @GET("/numbers")
    suspend fun requestNumbers(
        @Query("page_index") page_index : Int
    ) : Call<List<NumbersDTO>>


    // 특정 부서명 검색어에 의한 전화번호 리퀘스트, 페이징 적용 o
    @GET("/numbers/search")
    suspend fun reqeustNumbersBySearch(
        @Query("search_name") search_name : String,
        @Query("page_index") page_index : Int
    ) : Call<List<NumbersDTO>>
}