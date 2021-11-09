package com.example.jnucenter.mvvm.feature.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.jnucenter.R
import com.example.jnucenter.databinding.ActivityMainBinding
import com.example.jnucenter.mvvm.feature.number.NumberActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private var adRequest : AdRequest? = null
    lateinit var viewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 구글애드몹
        MobileAds.initialize(this, object: OnInitializationCompleteListener{
            override fun onInitializationComplete(p0: InitializationStatus) {
            }
        })
        adRequest = AdRequest.Builder().build()
        binding.mainGoogleAdview.loadAd(adRequest)



        // SearchView 외의 다른 뷰 터치시 키보드, 포커스 해제
        binding.mainConst.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.mainSearchView.windowToken, 0)

                binding.mainSearchView.clearFocus()
            }
        })



        // 뷰페이저 세팅
        viewPager = binding.iconViewpager
        val pagerAdapter = IconViewPagerAdapter(this)
        viewPager.adapter = pagerAdapter
        // 뷰페이저 인디케이터 세팅
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> {
                        binding.viewpagerIndicator1.setImageDrawable(getDrawable(R.drawable.main_icon_viewpager_shape_green))
                        binding.viewpagerIndicator2.setImageDrawable(getDrawable(R.drawable.main_icon_viewpager_shape_grey))
                        binding.viewpagerIndicator3.setImageDrawable(getDrawable(R.drawable.main_icon_viewpager_shape_grey))
                    }
                    1 -> {
                        binding.viewpagerIndicator1.setImageDrawable(getDrawable(R.drawable.main_icon_viewpager_shape_grey))
                        binding.viewpagerIndicator2.setImageDrawable(getDrawable(R.drawable.main_icon_viewpager_shape_green))
                        binding.viewpagerIndicator3.setImageDrawable(getDrawable(R.drawable.main_icon_viewpager_shape_grey))
                    }
                    2 -> {
                        binding.viewpagerIndicator1.setImageDrawable(getDrawable(R.drawable.main_icon_viewpager_shape_grey))
                        binding.viewpagerIndicator2.setImageDrawable(getDrawable(R.drawable.main_icon_viewpager_shape_grey))
                        binding.viewpagerIndicator3.setImageDrawable(getDrawable(R.drawable.main_icon_viewpager_shape_green))
                    }
                }
            }
        })


        // 서치뷰 intent 설정
        binding.mainSearchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(this@MainActivity, NumberActivity::class.java)
                startActivity(intent)
                return true
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()

        // 메모리 해제
        adRequest = null
    }
}