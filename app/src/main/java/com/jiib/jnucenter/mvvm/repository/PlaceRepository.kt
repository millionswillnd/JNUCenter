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


    suspend fun getPlaceByName(name: String) : Flow<PagingData<PlaceDTO>> {
        return Pager(
            config = PagingConfig(pageSize = 12),
            pagingSourceFactory = { PlacePagingSource(place_service, name, "true")}
        ).flow
    }

    suspend fun getPlaces() : Flow<PagingData<PlaceDTO>> {
        return Pager(
            config = PagingConfig(pageSize = 12),
            pagingSourceFactory = { PlacePagingSource(place_service, "","false") }
        ).flow
    }
}