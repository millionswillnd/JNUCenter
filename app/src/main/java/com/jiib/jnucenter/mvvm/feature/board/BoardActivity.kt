package com.jiib.jnucenter.mvvm.feature.board

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.ActivityBoardBinding
import com.jiib.jnucenter.mvvm.feature.main.CustomNewsTitle
import com.jiib.jnucenter.mvvm.feature.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *    동아리, 소모임 및 알뜰장터 게시판을 크롤링한 액티비티
 */
class BoardActivity : AppCompatActivity() {

    lateinit var binding : ActivityBoardBinding
    lateinit var viewmodel : BoardViewModel
    // 뉴스 커스텀뷰를 순차 처리하기 위한 리스트
    private var view_list : List<CustomNewsTitle>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board)

        // 뷰모델 초기화
        viewmodel = ViewModelProvider(this).get(BoardViewModel::class.java)

        // 세팅하는데 쓸 게시글 뷰 list
        view_list = listOf(
            binding.mainMeetingTitle1,
            binding.mainMeetingTitle2,
            binding.mainMeetingTitle3,
            binding.mainMeetingTitle4,
            binding.mainMeetingTitle5,
            binding.mainMeetingTitle6,
            binding.mainMarketTitle1,
            binding.mainMarketTitle2,
            binding.mainMarketTitle3,
            binding.mainMarketTitle4,
            binding.mainMarketTitle5,
            binding.mainMarketTitle6
        )



        // 각 게시판 글에 정보 세팅
        CoroutineScope(Dispatchers.IO).launch {
            viewmodel.getBoardList()
        }



        // 뷰모델의 게시판 글 리스트 observe
        viewmodel.board_list.observe(this, Observer {

            var i = 0
            for((title, link) in it){
                view_list!!.get(i).title!!.text = title
                // 링크로 이동
                view_list!!.get(i).setOnClickListener {
                    val intent = Intent()
                    intent.setAction(Intent.ACTION_VIEW)
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.setData(Uri.parse(link))
                    startActivity(intent)
                }
                ++i
            }
        })

        // 백 버튼 리스너
        binding.boardBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onRestart() {
        super.onRestart()
        if (view_list == null){
            view_list = listOf(
                binding.mainMeetingTitle1,
                binding.mainMeetingTitle2,
                binding.mainMeetingTitle3,
                binding.mainMeetingTitle4,
                binding.mainMeetingTitle5,
                binding.mainMeetingTitle6,
                binding.mainMarketTitle1,
                binding.mainMarketTitle2,
                binding.mainMarketTitle3,
                binding.mainMarketTitle4,
                binding.mainMarketTitle5,
                binding.mainMarketTitle6
            )
        }
    }

    override fun onStop() {
        super.onStop()
        view_list = null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (view_list!=null) view_list = null
    }
}