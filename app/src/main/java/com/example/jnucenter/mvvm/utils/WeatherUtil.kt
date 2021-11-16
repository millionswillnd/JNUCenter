package com.example.jnucenter.mvvm.utils

import android.content.Context
import android.icu.util.Calendar
import android.icu.util.TimeZone
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

        if(weather_info.contains("구름") || weather_info.contains("흐림")) weather_icon_info = "구름"
        else if(weather_icon_info.contains("맑음")) weather_icon_info = "해"
        else if(weather_icon_info.contains("눈")) weather_icon_info = "눈"
        else if(weather_icon_info.contains("비")
            || weather_icon_info.contains("폭우")
            || weather_icon_info.contains("우박")
            || weather_icon_info.contains("번개")
            || weather_icon_info.contains("소나기")) weather_icon_info = "비"
        else if(weather_icon_info.contains("안개")) weather_icon_info = "안개"
        else {
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


    // 온도에 따른 추천 외출 복장 리턴
    fun getRecommendedWear(temperature : String): String {

        // repository의 온도가 초기화 되지 않았을 때를 대비
        if(temperature == "-") return "none"

        // 섭씨온도 기호 앞에서 끊어주기
        val idx = temperature.indexOf("℃")
        val str = temperature.substring(0, idx)

        val temp = str.toInt()
        var recommand_wear = " "

        if(temp >= 27) recommand_wear = "찜통 더위에는 반팔티 반바지가 답이지 ㄹㅇㅋㅋ"
        else if(temp>=23 && temp<=26) recommand_wear = "약간 덥긴 한데 반팔도 얇은 긴팔도 오케이!"
        else if(temp>=20 && temp<=22) recommand_wear = "선선해지는 날씨. 후드티 슬랙스를 입고 갑시다!"
        else if(temp>=17 && temp<=19) recommand_wear = "쌀쌀해지는 날씨엔 니트에 청바지가 어떨까요?"
        else if(temp>=12 && temp<=18) recommand_wear = "어우 추워라 ㄷㄷ 자켓을 걸치고 가야겠어요"
        else if(temp>=6 && temp<=11) recommand_wear = "덜덜덜.. 코트를 입고 나갑시다"
        else if(temp<=5) recommand_wear = "얼어 죽겠어요! 이럴땐 패딩과 목도리가 답"

        return recommand_wear
    }
}