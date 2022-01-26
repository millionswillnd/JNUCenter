package com.jiib.jnucenter.mvvm.repository.network.retrofit.news

import retrofit2.Call
import retrofit2.http.GET

/**
 *   신문 Retrofit Api
 */

interface NewsApi{

    // 전대신문 뉴스의 title과 url 6쌍을 담은 JSON 객체를 받아온다
    @GET("/news")
    suspend fun requestNewsInfo() : Call<NewsDTO>
}