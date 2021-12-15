package com.jiib.jnucenter.mvvm.repository.network.retrofit.board

import com.jiib.jnucenter.mvvm.repository.network.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Retrofit

class BoardService {

    private val retrofit_client = RetrofitClient

    fun getBoardList() : Call<List<BoardDTO>> {
        val client: Retrofit = RetrofitClient.getInstance()
        val response = client.create(BoardApi::class.java).getBoardList()

        return response
    }
}