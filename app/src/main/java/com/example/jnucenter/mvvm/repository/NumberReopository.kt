package com.example.jnucenter.mvvm.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.jnucenter.mvvm.repository.network.retrofit.RetrofitClient
import com.example.jnucenter.mvvm.repository.network.retrofit.numbers.NumberApi
import com.example.jnucenter.mvvm.repository.network.retrofit.numbers.NumbersDTO
import com.example.jnucenter.mvvm.repository.network.retrofit.weather.WeatherDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class NumberReopository {

    private val retrofit_client = RetrofitClient

    // 전화번호 리스트
    val number_list : MutableLiveData<List<NumbersDTO>> = MutableLiveData()


    //학내 전체 전화번호 리스트를 리턴
    fun getNumbers(){
        val client : Retrofit = retrofit_client.getInstance()
        val response : Call<List<NumbersDTO>> = client.create(NumberApi::class.java).requestNumbers()
        response.enqueue(object : Callback<List<NumbersDTO>>{
            override fun onResponse(
                call: Call<List<NumbersDTO>>,
                response: Response<List<NumbersDTO>>
            ) {
                number_list.postValue(response.body())
            }

            override fun onFailure(call: Call<List<NumbersDTO>>, t: Throwable) {
                Log.d("number api failed : ", "실패")
            }
        })
    }
}