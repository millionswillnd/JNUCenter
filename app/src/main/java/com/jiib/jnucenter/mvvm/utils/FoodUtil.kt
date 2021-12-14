package com.jiib.jnucenter.mvvm.utils

import android.icu.util.Calendar
import android.icu.util.TimeZone
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class FoodUtil {

    fun getNowDate() : String {

        var now_date: String = ""
        var month: String = ""
        var date: String = ""

        // 현재 날짜와 현재 달을 구한다
        if(android.os.Build.VERSION.SDK_INT >= 26){
            val now = LocalDate.now().toString()
            val temp_array = now.split("-")

            now_date = temp_array[2]
            month = temp_array[1]
        }
        else {
            val now = Date()
            val format = SimpleDateFormat("mm-dd").format(now)
            val date = format.toString()
        }

        val cal = Calendar.getInstance()
        cal.timeZone = TimeZone.getTimeZone("Asia/Seoul")
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

        date = "$month-$now_date $dayOfWeek"

        return date
    }
}