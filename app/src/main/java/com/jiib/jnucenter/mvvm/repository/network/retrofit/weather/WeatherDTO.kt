package com.jiib.jnucenter.mvvm.repository.network.retrofit.weather

import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    @SerializedName("description") val description : String,
    @SerializedName("temp") val temp : String
    )
