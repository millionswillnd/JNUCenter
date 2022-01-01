package com.jiib.jnucenter.mvvm.repository.network.retrofit.weather

import com.google.gson.annotations.SerializedName

// 날씨 상태(눈, 구름 등) + 기온을 담은 DTO
data class WeatherDTO(
    @SerializedName("description") val description : String,
    @SerializedName("temp") val temp : String
    )
