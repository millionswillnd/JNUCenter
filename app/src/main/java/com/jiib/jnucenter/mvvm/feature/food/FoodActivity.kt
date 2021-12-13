package com.jiib.jnucenter.mvvm.feature.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.ActivityFoodBinding

class FoodActivity : AppCompatActivity() {

    lateinit var binding : ActivityFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_food)


    }
}