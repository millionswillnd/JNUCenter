package com.example.jnucenter.mvvm.repository.network.retrofit.weather
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface WeatherApi {

    // 날씨 정보를 서버에서 얻어온다
    @GET("/weather/now")
    fun requestWeatherInfo() : Call<WeatherDTO>
}