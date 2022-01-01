package com.jiib.jnucenter.mvvm.repository.network.retrofit.numbers

import com.google.gson.annotations.SerializedName

/**
 *    부서명, 부서전화번호, 초성을 담은 부서 전화번호 DTO
 */

data class NumbersDTO(
    @SerializedName("department_name") val department_name : String,
    @SerializedName("department_number") val department_number : String,
    // 추후 삭제
    @SerializedName("is_first") val is_first : String,
    @SerializedName("consonant") val consonant : String
)
