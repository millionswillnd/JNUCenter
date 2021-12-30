package com.jiib.jnucenter.mvvm.utils

import android.util.Log

/*
*     지도 액티비티에서 쓰일 Util Class
 */
class PlaceUtil {

    // 두 지점의 위경도 차이로 거리를 계산해 리턴
    fun getDistance(latitude_one: Double, latitude_two: Double, longitude_one: Double, longitude_two: Double) : Double{

        val temp = longitude_one - longitude_two
        var distance = Math.sin(degToRadians(latitude_one)) * Math.sin(degToRadians(latitude_two)) + Math.cos(degToRadians(latitude_one)) * Math.cos(degToRadians(latitude_two)) * Math.cos(degToRadians(temp))

        distance = Math.acos(distance)
        distance = radiansToDeg(distance)
        distance = distance * 60 * 1.1515
        val meter_distance = distance * 1609.344

        // 시간 계산을 위해 Meter 단위로 리턴
        return meter_distance
    }


    // demical degrees -> radians
    private fun degToRadians(degrees: Double) : Double {
        return (degrees * Math.PI / 180.0)
    }


    // radians -> demical degrees
    private fun radiansToDeg(radian: Double) : Double {
        return (radian * 180 / Math.PI)
    }


    // 거리를 보행 시간으로 변환해 리턴
    fun getTimeByDistance(distance: Double) : Array<Double?> {
        val time_array = arrayOf<Double?>(null, null)

        // 보통 속도로 보행 시 걸리는 시간
        time_array[0] = (distance/80)
        // 뛰어갈 시 걸리는 시간
        time_array[1] = (distance/300)

        return time_array
    }
}