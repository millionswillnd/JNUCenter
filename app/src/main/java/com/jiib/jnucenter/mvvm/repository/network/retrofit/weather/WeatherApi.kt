package com.jiib.jnucenter.mvvm.repository.network.retrofit.weather
import retrofit2.Call
import retrofit2.http.GET

/**
 *   Weather Retrofit Api
 */

interface WeatherApi {

    // 날씨 정보를 서버에서 얻어온다
    @GET("/weather")
    suspend fun requestWeatherInfo() : Call<WeatherDTO>
}