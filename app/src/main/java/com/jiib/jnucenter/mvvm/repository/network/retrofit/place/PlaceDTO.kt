package com.jiib.jnucenter.mvvm.repository.network.retrofit.place

import com.google.gson.annotations.SerializedName

data class PlaceDTO(
    @SerializedName("school_department") val school_department : String,
    @SerializedName("school_places") val school_places : String,
    @SerializedName("school_location") val school_location : String,
    @SerializedName("school_way") val school_way : String
)