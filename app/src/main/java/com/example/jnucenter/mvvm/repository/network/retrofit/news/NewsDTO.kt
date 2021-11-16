package com.example.jnucenter.mvvm.repository.network.retrofit.news

import com.google.gson.annotations.SerializedName

data class NewsDTO(
    @SerializedName("news_list") val news_list : LinkedHashMap<String, String>
)
