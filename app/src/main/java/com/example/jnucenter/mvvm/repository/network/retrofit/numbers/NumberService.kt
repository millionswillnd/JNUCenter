package com.example.jnucenter.mvvm.repository.network.retrofit.numbers

import android.util.Log
import com.example.jnucenter.mvvm.repository.network.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class NumberService{

    private val retrofit_client = RetrofitClient

    fun getNumbers(page_index : Int) : List<NumbersDTO>? {
        val client : Retrofit = retrofit_client.getInstance()
        val response : Call<List<NumbersDTO>> = client.create(NumberApi::class.java).requestNumbers(page_index)
        var numbers : List<NumbersDTO>? = null

        Log.d("테스트", " 4")
        response.enqueue(object : Callback<List<NumbersDTO>> {
            override fun onResponse(
                call: Call<List<NumbersDTO>>,
                response: Response<List<NumbersDTO>>
            ) {
                numbers = response.body()
                Log.d("테스트", " 4.5.5")
            }

            override fun onFailure(call: Call<List<NumbersDTO>>, t: Throwable) {
                Log.d("number api failed : ", "실패")
                Log.d("테스트", " 5")
            }
        })

        Log.d("테스트", "6 ${numbers!!.get(0).department_name}")

        return numbers
    }

}