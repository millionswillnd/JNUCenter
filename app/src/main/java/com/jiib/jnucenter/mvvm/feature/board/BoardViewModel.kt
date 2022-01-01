package com.jiib.jnucenter.mvvm.feature.board

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiib.jnucenter.mvvm.repository.BoardRepository
import com.jiib.jnucenter.mvvm.repository.network.retrofit.board.BoardDTO

/**
 *    게시판 뷰모델
 */
class BoardViewModel : ViewModel() {

    val repositry = BoardRepository()

    // 각 게시판의 글 title과 url을 담은 LiveData
    val board_list : MutableLiveData<List<BoardDTO>>
        get() = repositry.board_list


    // 게시판 글 리스트 api 요청
    fun getBoardList(){
        repositry.getBoardList()
    }

}