package com.jiib.jnucenter.mvvm.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat

/**
 *    장소검색 관련 유틸 클래스
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


    // 사용자 현재 위치 리턴
    fun getCurrentPosition(context: Context) : Array<Double> {
        val loc_manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val loc_provider = LocationManager.GPS_PROVIDER
        var current_loc : Location? = null

        // 해당 메서드는 권한 설정된 스코프에서만 호출 가능
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED) {
            current_loc = loc_manager.getLastKnownLocation(loc_provider)
        }

        return arrayOf(current_loc!!.latitude, current_loc.longitude )
    }
}