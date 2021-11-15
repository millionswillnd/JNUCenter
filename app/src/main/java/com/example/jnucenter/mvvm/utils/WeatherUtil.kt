package com.example.jnucenter.mvvm.utils

import android.content.Context
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.jnucenter.R

class WeatherUtil {

    // 현재 월, 날짜, 요일 구하기
    fun getCurrentDate() : String {
        val cal = Calendar.getInstance()
        cal.timeZone = TimeZone.getTimeZone("Asia/Seoul")

        val month = cal.get(Calendar.MONTH)+1
        val day = cal.get(Calendar.DATE)
        val dayNumb = cal.get(Calendar.DAY_OF_WEEK)
        var dayOfWeek = ""

        when(dayNumb){
            1 -> dayOfWeek = "일"
            2 -> dayOfWeek = "월"
            3 -> dayOfWeek = "화"
            4 -> dayOfWeek = "수"
            5 -> dayOfWeek = "목"
            6 -> dayOfWeek = "금"
            7 -> dayOfWeek = "토"
        }

        val date = "${month}/${day} ${dayOfWeek}"
        return date
    }

    // 적절한 날짜 아이콘 정보를 리턴
    fun getWeatherIconInfo(weather_info : String) : String {

        var weather_icon_info : String = " "

        if(weather_info.contains("구름")) weather_icon_info = "구름"
        else if(weather_icon_info.contains("맑음")) weather_icon_info = "해"
        else if(weather_icon_info.contains("눈")) weather_icon_info = "눈"
        else if(weather_icon_info.contains("비")
            || weather_icon_info.contains("번개")
            || weather_icon_info.contains("소나기")) weather_icon_info = "비"
        else if(weather_icon_info.contains("안개")) weather_icon_info = "안개"
        else {
            Log.d("확인dd", "$weather_icon_info")
            weather_icon_info = "해"
        }

        return weather_icon_info
    }

    // 정보에 따른 날씨 아이콘 리턴
    fun getWeatherIcon(icon_info: String, context: Context) : Int {

        var icon_res : Int = 0

        if(icon_info == "구름") icon_res = R.drawable.main_weather_cloud
        else if(icon_info == "해") icon_res = R.drawable.ic_baseline_wb_sunny_24
        else if(icon_info == "눈") icon_res = R.drawable.main_weather_snow
        else if(icon_info == "비") icon_res = R.drawable.main_weather_rain
        else if(icon_info == "안개") icon_res = R.drawable.main_weather_fog
        else icon_res = R.drawable.ic_baseline_wb_sunny_24

        return icon_res
    }
}