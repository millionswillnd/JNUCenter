package com.jiib.jnucenter.mvvm.repository.network.retrofit.board

import retrofit2.Call
import retrofit2.http.GET

/**
 *   소모임, 알뜰장터 게시판 관련 Retrofit Api
 */

interface BoardApi {

    @GET("/board/list")
    suspend fun getBoardList(): Call<List<BoardDTO>>
}