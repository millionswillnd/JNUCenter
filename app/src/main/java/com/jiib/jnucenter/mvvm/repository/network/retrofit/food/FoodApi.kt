package com.jiib.jnucenter.mvvm.repository.network.retrofit.food

import retrofit2.Call
import retrofit2.http.GET

interface FoodApi {

    @GET("/food/list")
    fun getFoodList(): Call<FoodDTO>
}