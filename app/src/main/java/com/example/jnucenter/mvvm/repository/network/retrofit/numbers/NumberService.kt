package com.example.jnucenter.mvvm.repository.network.retrofit.numbers

import android.util.Log
import com.example.jnucenter.mvvm.repository.network.retrofit.RetrofitClient
import kotlinx.coroutines.*
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class NumberService{

    private val retrofit_client = RetrofitClient

    suspend fun getNumbers(page_index : Int) : List<NumbersDTO>? {
        val client : Retrofit = retrofit_client.getInstance()
        var numbers : List<NumbersDTO>? = null

        val response : Call<List<NumbersDTO>> = client.create(NumberApi::class.java).requestNumbers(page_index)
        withContext(Dispatchers.IO){
                numbers = response.execute().body()
            }

        return numbers
    }

}