package com.example.jnucenter.mvvm.feature.number

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jnucenter.R
import com.example.jnucenter.databinding.ActivityNumberBinding
import com.example.jnucenter.mvvm.repository.NumberRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NumberActivity : AppCompatActivity() {

    lateinit var binding : ActivityNumberBinding
    lateinit var adapter: NumberAdapter
    lateinit var number_viewmodel : NumberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_number)
        // 뷰모델
        number_viewmodel = ViewModelProvider(this).get(NumberViewModel::class.java)
        // 리사이클러뷰, 페이징
        adapter = NumberAdapter()
        binding.numberRecyclerview.adapter = adapter
        binding.numberRecyclerview.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            number_viewmodel.getNumbers().collectLatest {
                adapter.submitData(it)
            }
        }


    }
}