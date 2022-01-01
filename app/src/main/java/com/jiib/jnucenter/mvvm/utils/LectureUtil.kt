package com.jiib.jnucenter.mvvm.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

/**
 *    강의기한 관련 유틸 클래스
 */

class LectureUtil {

    // 강의 퀴즈 등의 기한과 현재 날짜와의 차이를 리턴
    fun getReservedDate(date : String) : Int{

        // 현재 날짜
        var now_date: Int = 0
        // 달과 달 사이의 기간 차를 구하기 위한 변수들
        var first_month: Int = 0
        var second_month: Int = 0


        // ex. 2021년 12월 31일 이런식으로 date는 구성되어있다. 마지막 날짜와 해당 달을 구해준다
        val temp : List<String> = date.split(" ")
        val last_date = temp[2].substring(0, temp[2].length-1).toInt()
        second_month = temp[1].substring(0, temp[1].length-1).toInt()

        // 현재 날짜와 현재 달을 구한다
        if(android.os.Build.VERSION.SDK_INT >= 26){
            val now = LocalDate.now().toString()
            val temp_array = now.split("-")

            now_date = temp_array[2].toInt()
            first_month = temp_array[1].toInt()
        }
        else {
            val now = Date()
            val format = SimpleDateFormat("dd").format(now)
            now_date = format.toInt()
            first_month = SimpleDateFormat("mm").format(now).toInt()

        }

        Log.d("날짜 확인 : ", "$now_date")

        // 현재 날짜와의 차이를 리턴
        // 서로 달이 다를 경우 별도의 메소드를 통해 차이를 리턴
        if(first_month == second_month) return last_date - now_date
        else return getDateBetweenMonth(first_month, second_month, now_date, last_date)

    }

    // 만약 11월 28일과 12월 2일의 차이 처럼 달이 넘어가는 경우 30인지 31일이 마지막인지 고려해줘야 한다
    // 1. 두달 이상 차이는 고려x     2. 학기 일정상 연도가 넘어가는 강의 또한 없기에 고려 x
    fun getDateBetweenMonth(first_month: Int, second_month: Int ,first_date: Int, second_date: Int) : Int {

        var temp: Int = 0
        var reserved: Int = 0

        // 1~7월은 홀수달이 31일, 짝수달이 30일이다.
        if(first_month < 8){
            // 2월은 28일이 마지막
            if(first_month == 2){
               temp = 28 - first_date
               reserved = temp + second_date
            }
            else{
                // 홀수달
                if (first_month%2 == 1){
                    temp = 31 - first_date
                    reserved = temp + second_date
                } // 짝수달
                else {
                    temp = 30 - first_date
                    reserved = temp + second_date
                }

            }
       }
       // 8~12월은 홀수달이 30일, 짝수달이 31일이다.
       else {
           if(first_month%2 == 1){
               temp = 30 - first_date
               reserved = temp + second_date
           }
           else{
               temp = 31 - first_date
               reserved = temp + second_date
           }
        }

        return reserved
    }
}