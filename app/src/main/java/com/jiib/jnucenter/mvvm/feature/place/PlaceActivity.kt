package com.jiib.jnucenter.mvvm.feature.place

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.ActivityPlaceBinding

class PlaceActivity : AppCompatActivity() {

    lateinit var binding : ActivityPlaceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_place)

        

    }
}