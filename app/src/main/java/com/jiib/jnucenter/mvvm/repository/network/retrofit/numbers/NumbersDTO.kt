package com.jiib.jnucenter.mvvm.repository.network.retrofit.numbers

import com.google.gson.annotations.SerializedName

data class NumbersDTO(
    @SerializedName("department_name") val department_name : String,
    @SerializedName("department_number") val department_number : String,
    @SerializedName("is_first") val is_first : String,
    @SerializedName("consonant") val consonant : String
)
