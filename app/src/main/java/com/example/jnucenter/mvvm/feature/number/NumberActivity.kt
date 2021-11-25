package com.example.jnucenter.mvvm.feature.number

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jnucenter.R
import com.example.jnucenter.databinding.ActivityNumberBinding
import com.example.jnucenter.mvvm.feature.main.MainActivity
import com.example.jnucenter.mvvm.repository.NumberRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.jar.Manifest

class NumberActivity : AppCompatActivity() {

    lateinit var binding : ActivityNumberBinding
    lateinit var adapter: NumberAdapter
    lateinit var number_viewmodel : NumberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_number)
        // 뷰모델 초기화
        number_viewmodel = ViewModelProvider(this).get(NumberViewModel::class.java)
        // 리사이클러뷰, 페이징 초기화
        adapter = NumberAdapter(this)
        binding.numberRecyclerview.adapter = adapter
        binding.numberRecyclerview.layoutManager = LinearLayoutManager(this)

        // 백버튼 리스너
        binding.numberBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        CoroutineScope(Dispatchers.IO).launch {
            number_viewmodel.getNumbers().collectLatest {
                adapter.submitData(it)
            }
        }
    }

}