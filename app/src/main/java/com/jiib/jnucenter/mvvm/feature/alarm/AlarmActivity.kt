package com.jiib.jnucenter.mvvm.feature.alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {

    lateinit var binding : ActivityAlarmBinding
    lateinit var viewmodel : AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm)

        // 뷰모델 초기화
        viewmodel = ViewModelProvider(this).get(AlarmViewModel::class.java)

    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}