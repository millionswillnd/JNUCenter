package com.jiib.jnucenter.mvvm.repository


import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jiib.jnucenter.mvvm.repository.network.retrofit.place.PlaceDTO
import com.jiib.jnucenter.mvvm.repository.network.retrofit.place.PlacePagingSource
import com.jiib.jnucenter.mvvm.repository.network.retrofit.place.PlaceService
import com.jiib.jnucenter.mvvm.utils.PlaceUtil
import kotlinx.coroutines.flow.Flow

/**
 *   장소 관련 Repository
 */

class PlaceRepository {

    private val place_service = PlaceService()
    private val place_util = PlaceUtil()


    // 검색어에 해당하는 장소목록 paging data 리턴
    fun getPlacesByName(name: String) : Flow<PagingData<PlaceDTO>> {
        return Pager(
            config = PagingConfig(pageSize = 12),
            pagingSourceFactory = { PlacePagingSource(place_service, name, true)}
        ).flow
    }

    // 전체 장소목록 paging으로 데려오기
    fun getPlaces() : Flow<PagingData<PlaceDTO>> {
        return Pager(
            config = PagingConfig(pageSize = 12),
            pagingSourceFactory = { PlacePagingSource(place_service, "",false) }
        ).flow
    }


    // 사용자 현재 위치 리턴
    fun getCurrentPosition(context: Context) : Array<Double> {
        return place_util.getCurrentPosition(context)
    }

    // 두 지점의 위경도 차이로 거리를 계산해 리턴
    fun getDistance(latitude_one: Double, latitude_two: Double, longitude_one: Double, longitude_two: Double) : Double{
        return place_util.getDistance(latitude_one, latitude_two, longitude_one, longitude_two)
    }

    // 거리를 보행 시간으로 변환해 리턴
    fun getTimeByDistance(distance: Double) : Array<Double?>{
        return place_util.getTimeByDistance(distance)
    }
}