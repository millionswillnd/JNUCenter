package com.jiib.jnucenter.mvvm.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jiib.jnucenter.mvvm.repository.network.retrofit.RetrofitClient
import com.jiib.jnucenter.mvvm.repository.network.retrofit.news.NewsApi
import com.jiib.jnucenter.mvvm.repository.network.retrofit.news.NewsDTO
import com.jiib.jnucenter.mvvm.repository.network.retrofit.weather.WeatherApi
import com.jiib.jnucenter.mvvm.repository.network.retrofit.weather.WeatherDTO
import com.jiib.jnucenter.mvvm.utils.WeatherUtil
import retrofit2.*

/**
 *   홈화면 관련 Repository
 */

class MainRepository {

    private val retrofit_client = RetrofitClient
    private val weather_util = WeatherUtil()

    // 날씨 정보
    val weather_description : MutableLiveData<String> = MutableLiveData("")
    val weather_temperature : MutableLiveData<String> = MutableLiveData("")

    // 현재 날짜
    val now_date : MutableLiveData<String> = MutableLiveData("")

    // 날씨 아이콘 정보
    val weather_icon_info : MutableLiveData<String> = MutableLiveData("")

    // 온도에 따른 옷 추천
    val recommand_wear : MutableLiveData<String> = MutableLiveData("")

    // 뉴스 title, url 6쌍 (순서)
    val news_list : MutableLiveData<LinkedHashMap<String, String>> = MutableLiveData();




    // 날씨 api 요청
    fun getWeathers(){
        val client : Retrofit = retrofit_client.getInstance()
        val response : Call<WeatherDTO> = client.create(WeatherApi::class.java).requestWeatherInfo()
        response.enqueue(object : Callback<WeatherDTO>{

            override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                weather_description.postValue(response.body()?.description)
                weather_temperature.postValue(response.body()?.temp)

            }

            override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                Log.d("weather api failed : ", "실패")
            }
        })
        return
    }

    // 날짜 구하기
    fun getDate(){
        val date = weather_util.getCurrentDate()
        now_date.postValue(date)
    }

    // 날씨 아이콘 정보 구하기
    fun getWeatherIconInfo(){
        val icon_info = weather_util.getWeatherIconInfo(weather_description.value!!)
        weather_icon_info.postValue(icon_info)
    }

    // 온도에 따른 추천 옷 구하기
    fun getRecommendedWear(){
        val rec_wear = weather_util.getRecommendedWear(weather_temperature.value!!)
        recommand_wear.postValue(rec_wear)
    }

    // 신문 title, url api
    fun getNews(){
        val client : Retrofit = retrofit_client.getInstance()
        val response : Call<NewsDTO> = client.create(NewsApi::class.java).requestNewsInfo()
        response.enqueue(object : Callback<NewsDTO>{
            override fun onResponse(call: Call<NewsDTO>, response: Response<NewsDTO>) {
                val temp : NewsDTO? = response.body()
                val map : LinkedHashMap<String, String>? = temp?.news_list

                news_list.postValue(map)
            }

            override fun onFailure(call: Call<NewsDTO>, t: Throwable) {
                Log.d("news api failed : ", "실패")
            }
        })
    }

}