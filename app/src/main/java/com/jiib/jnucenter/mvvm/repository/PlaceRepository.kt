package com.jiib.jnucenter.mvvm.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jiib.jnucenter.mvvm.repository.network.retrofit.numbers.NumberPagingSource
import com.jiib.jnucenter.mvvm.repository.network.retrofit.place.PlaceDTO
import com.jiib.jnucenter.mvvm.repository.network.retrofit.place.PlacePagingSource
import com.jiib.jnucenter.mvvm.repository.network.retrofit.place.PlaceService
import kotlinx.coroutines.flow.Flow

class PlaceRepository {

    val place_service = PlaceService()


    // 검색어에 해당하는 장소목록 paging으로 데려오기
    suspend fun getPlaceByName(name: String) : Flow<PagingData<PlaceDTO>> {
        return Pager(
            config = PagingConfig(pageSize = 12),
            pagingSourceFactory = { PlacePagingSource(place_service, name, "true")}
        ).flow
    }

    // 전체 장소목록 paging으로 데려오기
    suspend fun getPlaces() : Flow<PagingData<PlaceDTO>> {
        return Pager(
            config = PagingConfig(pageSize = 12),
            pagingSourceFactory = { PlacePagingSource(place_service, "","false") }
        ).flow
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