package com.jiib.jnucenter.mvvm.repository.network.retrofit.board

import retrofit2.Call
import retrofit2.http.GET

interface BoardApi {

    @GET("/board/list")
    fun getBoardList(): Call<List<BoardDTO>>
}