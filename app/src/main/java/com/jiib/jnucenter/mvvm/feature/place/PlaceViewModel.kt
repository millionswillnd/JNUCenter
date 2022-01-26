package com.jiib.jnucenter.mvvm.feature.place

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jiib.jnucenter.mvvm.feature.record.RecordViewModel
import com.jiib.jnucenter.mvvm.repository.PlaceRepository
import com.jiib.jnucenter.mvvm.repository.network.retrofit.place.PlaceDTO
import kotlinx.coroutines.flow.Flow

/**
 *     장소 액티비티 뷰모델
 */

class PlaceViewModel (application: Application) : AndroidViewModel(application) {

    private val repository = PlaceRepository()
    private val context = getApplication<Application>().applicationContext

    // 맵 위치의 경도
    val latitude : MutableLiveData<String> = MutableLiveData()
    // 맵 위치의 위도
    val longitude : MutableLiveData<String> = MutableLiveData()
    // 장소 이름
    val place_name : MutableLiveData<String> = MutableLiveData()
    // 장소 힌트
    val way : MutableLiveData<String> = MutableLiveData()

    // 현재 기기 위치 경도
    val current_latitutde : MutableLiveData<String> = MutableLiveData()
    // 현재 기기 위치 위도
    val current_longitude : MutableLiveData<String> = MutableLiveData()


    // 검색어로 장소 찾기 리파지토리 api 요청
    suspend fun getPlacesByName(name:String) : Flow<PagingData<PlaceDTO>> {
        return repository.getPlacesByName(name).cachedIn(viewModelScope)
    }

    // 전체 장소 목록 리파지토리 api 요청
    suspend fun getPlaces() : Flow<PagingData<PlaceDTO>> {
        return repository.getPlaces().cachedIn(viewModelScope)
    }

    // 현재 기기의 위치 업데이트
    fun getCurrentLocation() {
        // 위도 경도를 담은 배열
        val arr = repository.getCurrentPosition(context)

        current_latitutde.postValue(arr[0].toString())
        current_longitude.postValue(arr[1].toString())
    }

    // 두 지점의 거리 차이 구하는 api
    fun getDistance(latitude_one: Double, latitude_two: Double, longitude_one: Double, longitude_two: Double) : Double{
        return repository.getDistance(latitude_one, latitude_two, longitude_one, longitude_two)
    }

    // 거리를 걸리는 시간으로 변환해 배열로 리턴하는 api
    fun getTimeByDistance(distance: Double) : Array<Double?>{
        return repository.getTimeByDistance(distance)
    }

    // context를 쓰기 위한 뷰모델 팩토리
    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PlaceViewModel(application) as T
        }
    }
}