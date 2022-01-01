package com.jiib.jnucenter.mvvm.feature.lecture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.ActivityLectureDateBinding
import com.jiib.jnucenter.mvvm.feature.main.MainActivity
import com.jiib.jnucenter.mvvm.repository.network.retrofit.lecture.LectureDTO
import com.jiib.jnucenter.mvvm.utils.LectureUtil
import kotlinx.coroutines.*

/**
 *    강의 기한 액티비티
 */
class LectureDateActivity : AppCompatActivity() {


    lateinit var binding : ActivityLectureDateBinding
    lateinit var adapter : LectureAdapter
    lateinit var lecture_viewModel: LectureDateViewModel
    // 유틸클래스 및 dto 담은 리스트
    var lecture_util : LectureUtil? = null
    var lecture_list : List<LectureDTO>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lecture_date)

        // 뷰모델 초기화
        lecture_viewModel = ViewModelProvider(this).get(LectureDateViewModel::class.java)

        // 유틸클래스 초기화
        lecture_util = LectureUtil()

        // 리사이클러뷰 초기화
        val recyclerview = binding.lectureRecyclerview
        lecture_list = runBlocking(Dispatchers.IO) {
            lecture_viewModel.getLectureDates()!!
        }
        adapter = LectureAdapter(lecture_util!! , lecture_list!!, this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)


        // 백버튼 리스너
        binding.lectureBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        lecture_util = LectureUtil()
        lecture_list = runBlocking(Dispatchers.IO) {
            lecture_viewModel.getLectureDates()!!
        }
    }

    override fun onStop() {
        super.onStop()
        lecture_util = null
        lecture_list = null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (lecture_util != null) lecture_util = null
        if (lecture_list != null) lecture_list = null
    }

}