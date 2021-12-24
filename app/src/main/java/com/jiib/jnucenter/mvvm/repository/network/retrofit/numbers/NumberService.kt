package com.jiib.jnucenter.mvvm.repository.network.retrofit.numbers

import com.jiib.jnucenter.mvvm.repository.network.retrofit.RetrofitClient
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Retrofit

class NumberService{

    private val retrofit_client = RetrofitClient

    // 장소 전체 목록 get
    suspend fun getNumbers(page_index : Int) : List<NumbersDTO>? {
        val client : Retrofit = retrofit_client.getInstance()
        var numbers : List<NumbersDTO>? = null

        val response : Call<List<NumbersDTO>> = client.create(NumberApi::class.java).requestNumbers(page_index)
        withContext(Dispatchers.IO){
                numbers = response.execute().body()
            }
        return numbers
    }

    // 검색어 서치
    suspend fun getNumbersBySearch(search_name : String, page_index: Int) : List<NumbersDTO>? {
        val client = retrofit_client.getInstance()
        var numbers : List<NumbersDTO>? = null
        val response : Call<List<NumbersDTO>>
            = client.create(NumberApi::class.java).reqeustNumbersBySearch(search_name, page_index)

        withContext(Dispatchers.IO){
            numbers = response.execute().body()
        }

        return numbers
    }
}