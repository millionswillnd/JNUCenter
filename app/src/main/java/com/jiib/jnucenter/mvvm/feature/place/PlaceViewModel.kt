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

class PlaceViewModel : ViewModel() {

    private val repository = PlaceRepository()

    // 경도
    val latitude : MutableLiveData<String> = MutableLiveData()
    // 위도
    val longitude : MutableLiveData<String> = MutableLiveData()
    // 장소 이름
    val place_name : MutableLiveData<String> = MutableLiveData()
    // way
    val way : MutableLiveData<String> = MutableLiveData()

    // 현재 기기 위치 경도
    val current_latitutde : MutableLiveData<String> = MutableLiveData()
    // 현재 기기 위치 위도
    val current_longitude : MutableLiveData<String> = MutableLiveData()


    // 검색어로 장소 찾기 리파지토리 api 요청
    suspend fun getPlaceByName(name:String) : Flow<PagingData<PlaceDTO>> {
        return repository.getPlaceByName(name).cachedIn(viewModelScope)
    }

    // 전체 장소 목록 리파지토리 api 요청
    suspend fun getPlaces() : Flow<PagingData<PlaceDTO>> {
        return repository.getPlaces().cachedIn(viewModelScope)
    }

    // 현재 기기의 위치 업데이트
    fun getCurrentLocation(context : Context) {
        // 위도 경도를 담은 배열
        val arr = repository.getCurrentPosition(context)

        current_latitutde.postValue(arr[0].toString())
        current_longitude.postValue(arr[1].toString())
    }
}