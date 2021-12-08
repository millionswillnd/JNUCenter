package com.jiib.jnucenter.mvvm.feature.number

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.ActivityNumberBinding
import com.jiib.jnucenter.mvvm.feature.main.MainActivity
import com.jiib.jnucenter.mvvm.utils.TextUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NumberActivity : AppCompatActivity() {

    lateinit var binding : ActivityNumberBinding
    lateinit var adapter: NumberAdapter
    lateinit var number_viewmodel : NumberViewModel
    var text_utils : TextUtils? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_number)

        // 뷰모델 초기화
        number_viewmodel = ViewModelProvider(this).get(NumberViewModel::class.java)

        // 유틸 클래스 초기화
        text_utils = TextUtils()

        // 리사이클러뷰, 페이징 초기화
        adapter = NumberAdapter(this, text_utils!!)
        binding.numberRecyclerview.adapter = adapter
        binding.numberRecyclerview.layoutManager = LinearLayoutManager(this)

        // 백버튼 리스너
        binding.numberBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 검색 필터 리스너
        binding.numberSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            // 텍스트가 바뀔 때 마다 adpater를 갱신
            override fun onQueryTextChange(newText: String?): Boolean {

                lifecycleScope.launch(Dispatchers.IO) {
                    number_viewmodel.getNumbersBySearch(newText!!).collectLatest {
                        adapter.submitData(it)
                    }
                }
                return true
            }


            override fun onQueryTextSubmit(query: String?): Boolean {
                // 포커스를 해제한다
                binding.numberSearchView.clearFocus()
                return true
            }
        })



        // 뷰모델에 전화번호 리스트를 요청하고 어댑터에 제공한다(페이징)
        CoroutineScope(Dispatchers.IO).launch {
            number_viewmodel.getNumbers().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onRestart() {
        super.onRestart()

        text_utils = TextUtils()
    }

    override fun onStop() {
        super.onStop()

        text_utils = null
    }

    override fun onDestroy() {
        super.onDestroy()

        if (text_utils != null) text_utils = null
    }

}