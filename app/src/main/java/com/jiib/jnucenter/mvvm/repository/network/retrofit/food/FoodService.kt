package com.jiib.jnucenter.mvvm.repository.network.retrofit.food

import com.jiib.jnucenter.mvvm.repository.network.retrofit.RetrofitClient
import com.jiib.jnucenter.mvvm.repository.network.retrofit.alarm.AlarmApi
import retrofit2.Call
import retrofit2.Retrofit

class FoodService {

    private val retrofit_client = RetrofitClient

    fun getFoodList() : Call<FoodDTO> {
        val client : Retrofit = retrofit_client.getInstance()
        val response = client.create(FoodApi::class.java).getFoodList()
        return response
    }
}