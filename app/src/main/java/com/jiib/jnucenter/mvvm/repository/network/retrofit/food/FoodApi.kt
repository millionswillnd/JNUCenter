package com.jiib.jnucenter.mvvm.repository.network.retrofit.food

import retrofit2.Call
import retrofit2.http.GET

/**
 *    학식 Retrofit Api
 */

interface FoodApi {

    // 식당별 학식 리스트를 리퀘스트
    @GET("/food/list")
    suspend fun getFoodList(): Call<List<FoodDTO>>
}