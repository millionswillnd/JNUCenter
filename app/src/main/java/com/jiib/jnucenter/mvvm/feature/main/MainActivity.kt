package com.jiib.jnucenter.mvvm.feature.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.jiib.jnucenter.R
import com.jiib.jnucenter.databinding.ActivityMainBinding
import com.jiib.jnucenter.mvvm.feature.number.NumberActivity
import com.jiib.jnucenter.mvvm.utils.WeatherUtil
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import kotlinx.coroutines.*

/**
 *   홈화면 액티비티
 */
class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    // 구글 ads 관련
    private var adRequest : AdRequest? = null
    lateinit var viewPager : ViewPager2
    lateinit var viewModel : MainViewModel
    var weather_util : WeatherUtil? = null
    // 뉴스 리스트를 담을 배열
    private var view_title_list : List<CustomNewsTitle>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        // 뷰모델 초기화
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        // 신문 title과 url을 담기위한 xml상의 view들 담기
        view_title_list = listOf<CustomNewsTitle>(
            binding.mainNewsTitle1,
            binding.mainNewsTitle2,
            binding.mainNewsTitle3,
            binding.mainNewsTitle4,
            binding.mainNewsTitle5,
            binding.mainNewsTitle6
        )

        // 날씨 서버 통신
        CoroutineScope(Dispatchers.IO).launch {
            // 날씨 정보 신청
            viewModel.getWeathers()
            viewModel.getDate()
            // 신문 정보 신청
            viewModel.getNewsList()

        }

        // 날씨 아이콘 정보 세팅
        viewModel.weather_description.observe(this, Observer {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getWeatherIconInfo()
            }
        })

        // 날씨 아이콘 세팅
        weather_util = WeatherUtil()
        viewModel.weather_icon_info.observe(this, Observer {
            binding.mainWeatherImage.setImageDrawable(ContextCompat
                .getDrawable(this, weather_util!!.getWeatherIcon(it, this)))
        })


        // 온도에 따른 추천 옷 세팅
        viewModel.weather_temperature.observe(this, Observer {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getRecommendedWear()
            }
            binding.maniWeatherCloth.text = viewModel.recommand_wear.value
        })


        // 신문 title, url 세팅
        viewModel.news_map.observe(this, Observer{
            var i = 0;

            for ((news_title,news_url) in it){
                val list_unit = view_title_list?.get(i)

                list_unit?.title?.text = news_title
                list_unit?.setOnClickListener {
                    val intent = Intent()
                    intent.setAction(Intent.ACTION_VIEW)
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.setData(Uri.parse(news_url))
                    startActivity(intent)
                }

                i++
            }
        })

        // 구글애드몹 세팅
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

        // 서치뷰 검색 시 작업
        binding.mainSearchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(this@MainActivity, NumberActivity::class.java)
                intent.putExtra("query", query)
                startActivity(intent)
                return true
            }
        })
    }



    override fun onRestart() {
        super.onRestart()

        adRequest = AdRequest.Builder().build()
        view_title_list = listOf<CustomNewsTitle>(
            binding.mainNewsTitle1,
            binding.mainNewsTitle2,
            binding.mainNewsTitle3,
            binding.mainNewsTitle4,
            binding.mainNewsTitle5,
            binding.mainNewsTitle6
        )
        weather_util = WeatherUtil()
    }

    override fun onStop() {
        super.onStop()

        adRequest = null
        view_title_list = null
        weather_util = null
    }

    override fun onDestroy() {
        super.onDestroy()

        if(adRequest != null) adRequest = null
        if(view_title_list != null) view_title_list = null
        if(weather_util != null) weather_util = null
    }


}