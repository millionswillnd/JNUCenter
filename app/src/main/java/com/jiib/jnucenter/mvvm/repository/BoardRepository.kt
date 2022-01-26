package com.jiib.jnucenter.mvvm.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jiib.jnucenter.mvvm.repository.network.retrofit.board.BoardDTO
import com.jiib.jnucenter.mvvm.repository.network.retrofit.board.BoardService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   게시판 관련 Repository
 */

class BoardRepository {

    private val board_service = BoardService()

    // 각 게시판의 글 리스트와 url 링크를 담은 LiveData
    val board_list = MutableLiveData<List<BoardDTO>>()

    suspend fun getBoardList(){
        val response = board_service.getBoardList()
        response.enqueue(object : Callback<List<BoardDTO>>{
            override fun onResponse(
                call: Call<List<BoardDTO>>,
                response: Response<List<BoardDTO>>
            ) {
                board_list.postValue(response.body())
            }

            override fun onFailure(call: Call<List<BoardDTO>>, t: Throwable) {
                Log.d("board api failed : ", "실패")
            }
        })
    }
}