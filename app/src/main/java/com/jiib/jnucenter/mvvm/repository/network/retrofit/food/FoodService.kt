package com.jiib.jnucenter.mvvm.repository.network.retrofit.food

import com.jiib.jnucenter.mvvm.repository.network.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Retrofit

/**
 *   Board Retrofit Service
 */

class FoodService {

    private val retrofit_client = RetrofitClient

    // 모든 학식 정보 list를 가져온다
    suspend fun getFoodList() : Call<List<FoodDTO>> {
        val client : Retrofit = retrofit_client.getInstance()
        val response = client.create(FoodApi::class.java).getFoodList()
        return response
    }
}