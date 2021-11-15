package com.example.jnucenter.mvvm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jnucenter.mvvm.repository.network.retrofit.RetrofitClient
import com.example.jnucenter.mvvm.repository.network.retrofit.weather.WeatherApi
import com.example.jnucenter.mvvm.repository.network.retrofit.weather.WeatherDTO
import com.example.jnucenter.mvvm.utils.WeatherUtil
import retrofit2.*

class Repository {

    private val retrofit_client = RetrofitClient
    private val weather_util = WeatherUtil()

    // 날씨 정보
    val weather_description : MutableLiveData<String> = MutableLiveData("none")
    val weather_temperature : MutableLiveData<String> = MutableLiveData("0")

    // 현재 날짜
    val now_date : MutableLiveData<String> = MutableLiveData("0")

    // 날씨 아이콘 정보
    val weather_icon_info : MutableLiveData<String> = MutableLiveData("0")



    // 날씨 api 요청
    fun getWeathers(){
        val client : Retrofit = retrofit_client.getInstance()
        val response : Call<WeatherDTO> = client.create(WeatherApi::class.java).requestWeatherInfo()
        response.enqueue(object : Callback<WeatherDTO>{

            override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                weather_description.postValue(response.body()?.description)
                weather_temperature.postValue(response.body()?.temp)

            }

            override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                Log.d("weather api fail : ", "실패")
            }
        })

        return
    }

    // 날짜 구하기
    fun getDate(){
        val date = weather_util.getCurrentDate()
        now_date.postValue(date)
    }

    // 날씨 아이콘 정보 구하기
    fun getWeatherIconInfo(){
        val icon_info = weather_util.getWeatherIconInfo(weather_description.value!!)
        weather_icon_info.postValue(icon_info)
    }
}