package com.jiib.jnucenter.mvvm.feature.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
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


    // 검색어로 장소 찾기 리파지토리 api 요청
    suspend fun getPlaceByName(name:String) : Flow<PagingData<PlaceDTO>> {
        return repository.getPlaceByName(name).cachedIn(viewModelScope)
    }

    // 전체 장소 목록 리파지토리 api 요청
    suspend fun getPlaces() : Flow<PagingData<PlaceDTO>> {
        return repository.getPlaces().cachedIn(viewModelScope)
    }
}