package com.jiib.jnucenter.mvvm.repository.network.retrofit.place

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *   Place Retrofit Api
 */

interface PlaceApi {

    // 특정 글자를 포함한 건물 리스트 리퀘스트
    @GET("/place/search/list")
    suspend fun getPlacesByName(
        @Query("name") name: String,
        @Query("page_index") page_index : Int
    ) : Call<List<PlaceDTO>>

    // 페이지에 해당하는 장소 리스트 리퀘스트
    @GET("/place/list")
    suspend fun getPlaces(
        @Query("page_index") page_index : Int
    ) : Call<List<PlaceDTO>>
}

