package com.jiib.jnucenter.mvvm.repository

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
}