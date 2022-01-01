package com.jiib.jnucenter.mvvm.repository.network.retrofit.news

import com.google.gson.annotations.SerializedName

/**
 *  뉴스 제목, URL을 담은 리스트 DTO
 */

data class NewsDTO(
    @SerializedName("news_list") val news_list : LinkedHashMap<String, String>
)
