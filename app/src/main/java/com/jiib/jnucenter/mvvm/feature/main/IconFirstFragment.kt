package com.jiib.jnucenter.mvvm.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.MainIconFragOneBinding
import com.jiib.jnucenter.mvvm.feature.alarm.AlarmActivity
import com.jiib.jnucenter.mvvm.feature.board.BoardActivity
import com.jiib.jnucenter.mvvm.feature.food.FoodActivity
import com.jiib.jnucenter.mvvm.feature.lecture.LectureDateActivity
import com.jiib.jnucenter.mvvm.feature.place.PlaceActivity
import com.jiib.jnucenter.mvvm.feature.record.RecordActivity

class IconFirstFragment : Fragment(), View.OnClickListener {

    var binding : MainIconFragOneBinding? = null
    var mainContext : Context? = null

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
        if (binding == null) binding = DataBindingUtil
            .inflate(inflater, R.layout.main_icon_frag_one, container, false)
        val root : View? = binding?.root

        if(mainContext == null) mainContext = context

        // 클릭리스너 세팅
        binding!!.customview1.setOnClickListener(this)
        binding!!.customview2.setOnClickListener(this)
        binding!!.customview3.setOnClickListener(this)
        binding!!.customview4.setOnClickListener(this)
        binding!!.customview5.setOnClickListener(this)
        binding!!.customview6.setOnClickListener(this)


        return root
    }

    // 클릭리스너 작업
    override fun onClick(v: View?) {
        when(v?.id){
            // 알람
            R.id.customview1 -> {
                val intent = Intent(mainContext, AlarmActivity::class.java)
                startActivity(intent)
            }

            // 강의기한
            R.id.customview2 -> {
                val intent = Intent(mainContext, LectureDateActivity::class.java)
                startActivity(intent)
            }

            // 오늘의 학식
            R.id.customview3 -> {
                val intent = Intent(mainContext, FoodActivity::class.java)
                startActivity(intent)
            }

            // 건물 찾기
            R.id.customview4 -> {
                val intent = Intent(mainContext, PlaceActivity::class.java)
                startActivity(intent)
            }

            // 모임, 알뜰장터
            R.id.customview5 -> {
                val intent = Intent(mainContext, BoardActivity::class.java)
                startActivity(intent)
            }

            // 수업 녹음
            R.id.customview6 -> {
                val intent = Intent(mainContext, RecordActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mainContext = null
        binding = null
    }
}