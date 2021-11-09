package com.example.jnucenter.mvvm.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.jnucenter.R
import com.example.jnucenter.SplashActivity
import com.example.jnucenter.databinding.MainIconFragOneBinding

class IconFirstFragment : Fragment(), View.OnClickListener {

    lateinit var binding : MainIconFragOneBinding
    lateinit var mainContext : Context

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 액티비티의 context를 할당한다
        mainContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 데이터바인딩
        binding = DataBindingUtil.inflate(inflater, R.layout.main_icon_frag_one, container, false)
        val root : View? = binding.root

        // 클릭리스너 세팅
        binding.customview1.setOnClickListener(this)
        binding.customview2.setOnClickListener(this)
        binding.customview3.setOnClickListener(this)
        binding.customview4.setOnClickListener(this)
        binding.customview5.setOnClickListener(this)
        binding.customview6.setOnClickListener(this)


        return root
    }

    // 클릭리스너 작업
    override fun onClick(v: View?) {
        when(v?.id){
            // 알람
            R.id.customview1 -> {
                Toast.makeText(mainContext, "터치이벤트 입니다", Toast.LENGTH_LONG).show()
            }

            // 강의기한
            R.id.customview2 -> {
                Toast.makeText(mainContext, "터치이벤트 입니다", Toast.LENGTH_LONG).show()
            }

            // 오늘의 학식
            R.id.customview3 -> {
                Toast.makeText(mainContext, "터치이벤트 입니다", Toast.LENGTH_LONG).show()
            }

            // 건물 찾기
            R.id.customview4 -> {
                Toast.makeText(mainContext, "터치이벤트 입니다", Toast.LENGTH_LONG).show()
            }

            // 모임, 알뜰장터
            R.id.customview5 -> {
                Toast.makeText(mainContext, "터치이벤트 입니다", Toast.LENGTH_LONG).show()
            }

            // 수업 녹음
            R.id.customview6 -> {
                Toast.makeText(mainContext, "터치이벤트 입니다", Toast.LENGTH_LONG).show()
            }
        }
    }
}