package com.jiib.jnucenter.mvvm.repository.network.retrofit.board

import com.jiib.jnucenter.mvvm.repository.network.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Retrofit

/**
 *   Board Retrofit Service
 */

class BoardService {

    private val retrofit_client = RetrofitClient

    // 게시판 글들을 담은 DTO를 리스폰스로 받는다
    fun getBoardList() : Call<List<BoardDTO>> {
        val client: Retrofit = RetrofitClient.getInstance()
        val response = client.create(BoardApi::class.java).getBoardList()

        return response
    }
}