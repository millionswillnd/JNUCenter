package com.example.jnucenter.mvvm.feature.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jnucenter.mvvm.repository.Repository

class MainViewModel : ViewModel() {

    // Repository
    val repository : Repository = Repository()

    // 날씨 상태, 섭씨온도 값
    val weather_description : MutableLiveData<String>
            get() = repository.weather_description
    val weather_temperature : MutableLiveData<String>
            get() = repository.weather_temperature

    // 날짜 요일
    val now_date : MutableLiveData<String>
            get() = repository.now_date

    // 날씨 아이콘 정보
    val weather_icon_info : MutableLiveData<String>
            get() = repository.weather_icon_info



    // 날씨 정보값 요청
    fun getWeathers() {
        try{
            repository.getWeathers()
        } catch (e:Exception){
            e.printStackTrace()
        }
    }

    // 날짜 요청
    fun getDate(){
        repository.getDate()
    }

    // 날씨 아이콘 정보 요청
    fun getWeatherIconInfo(){
        repository.getWeatherIconInfo()
    }

}