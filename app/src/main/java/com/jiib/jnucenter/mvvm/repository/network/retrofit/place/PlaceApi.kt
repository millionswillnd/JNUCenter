package com.jiib.jnucenter.mvvm.repository.network.retrofit.place

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceApi {

    // 특정 이름을 가진 장소 위치 json get
    @GET("/place/search/list")
    fun getPlaceByName(
        @Query("name") name: String,
        @Query("page_index") page_index : Int
    ) : Call<List<PlaceDTO>>

    // 페이지에 해당하는 장소 리스트 get
    @GET("/place/list")
    fun getPlaces(
        @Query("page_index") page_index : Int
    ) : Call<List<PlaceDTO>>
}