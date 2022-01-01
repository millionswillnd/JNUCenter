package com.jiib.jnucenter.mvvm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jiib.jnucenter.mvvm.repository.network.retrofit.food.FoodDTO
import com.jiib.jnucenter.mvvm.repository.network.retrofit.food.FoodService
import com.jiib.jnucenter.mvvm.utils.FoodUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   학식 게시판 관련 Repository
 */

class FoodRepository {

    // Retrofit Service
    private val food_service = FoodService()
    // 유틸 클래스
    private val food_util = FoodUtil()

    // 식당별 학식 리스트를 담은 LiveData
    val food_list = MutableLiveData<List<FoodDTO>>()
    // 현재 날짜
    val now_date = MutableLiveData<String>(food_util.getNowDate())

    // 학식 식단을 받아오는 api
    fun getFoodList(){
        val response = food_service.getFoodList()
        response.enqueue(object : Callback<List<FoodDTO>>{
            override fun onResponse(call: Call<List<FoodDTO>>, response: Response<List<FoodDTO>>) {
                val response = response.body()
                food_list.postValue(response!!)
            }

            override fun onFailure(call: Call<List<FoodDTO>>, t: Throwable) {
                Log.d("food api failed : ", "실패")
            }
        })
    }
}