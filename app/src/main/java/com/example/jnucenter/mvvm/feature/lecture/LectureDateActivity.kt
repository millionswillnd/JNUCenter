package com.example.jnucenter.mvvm.feature.lecture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jnucenter.R
import com.example.jnucenter.databinding.ActivityLectureDateBinding
import com.example.jnucenter.mvvm.feature.main.MainActivity
import com.example.jnucenter.mvvm.repository.network.retrofit.lecture.LectureDTO
import com.example.jnucenter.mvvm.utils.LectureUtil
import kotlinx.coroutines.*

class LectureDateActivity : AppCompatActivity() {


    lateinit var binding : ActivityLectureDateBinding
    lateinit var adapter : LectureAdapter
    lateinit var lecture_viewModel: LectureDateViewModel
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