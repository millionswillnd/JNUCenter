package com.jiib.jnucenter.mvvm.repository.network.retrofit.place

import com.jiib.jnucenter.mvvm.repository.network.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

class PlaceService {

    private val retrofit_client = RetrofitClient


    suspend fun getPlaceByName(name: String, page_index: Int) : List<PlaceDTO> {
        var places : List<PlaceDTO>
        val client = retrofit_client.getInstance()
        val response = client.create(PlaceApi::class.java).getPlaceByName(name, page_index)

        withContext(Dispatchers.IO){
            places = response.execute().body()!!
        }

        return places
    }

    suspend fun getPlaces(page_index: Int) : List<PlaceDTO>{
        var places : List<PlaceDTO>
        val client = retrofit_client.getInstance()
        val response = client.create(PlaceApi::class.java).getPlaces(page_index)

        withContext(Dispatchers.IO){
            places = response.execute().body()!!
        }

        return places
    }
}