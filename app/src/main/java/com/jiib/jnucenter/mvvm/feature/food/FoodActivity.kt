package com.jiib.jnucenter.mvvm.feature.food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.ActivityFoodBinding
import com.jiib.jnucenter.mvvm.feature.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *   학식 정보 액티비티
 */
class FoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodBinding
    private lateinit var viewmodel: FoodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_food)

        viewmodel = ViewModelProvider(this).get(FoodViewModel::class.java)

        // 백 버튼 리스너
        binding.foodBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 학식 리스트 api 요청
        CoroutineScope(Dispatchers.IO).launch {
            viewmodel.getFoodList()
        }

        // 날짜 세팅
        viewmodel.food_date.observe(this, Observer {
            binding.foodDateTv.text = it
        })

        // 학식 리스트 api response가 오면 이에 맞게 메뉴를 세팅
        viewmodel.food_list.observe(this, Observer {
            // 1생
            binding.launchFirstStudentMenus.text = it.get(0).launch_menu
            binding.dinnerFirstStudentMenus.text = it.get(0).dinner_menu
            // 햇들마루
            binding.launchHatdleMenus.text = it.get(1).launch_menu
            binding.dinnerHatdleMenus.text = it.get(1).dinner_menu
        })
    }
}