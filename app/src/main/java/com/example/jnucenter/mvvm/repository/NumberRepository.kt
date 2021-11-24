package com.example.jnucenter.mvvm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.jnucenter.mvvm.repository.network.retrofit.RetrofitClient
import com.example.jnucenter.mvvm.repository.network.retrofit.numbers.NumberApi
import com.example.jnucenter.mvvm.repository.network.retrofit.numbers.NumberPagingSource
import com.example.jnucenter.mvvm.repository.network.retrofit.numbers.NumberService
import com.example.jnucenter.mvvm.repository.network.retrofit.numbers.NumbersDTO
import com.example.jnucenter.mvvm.repository.network.retrofit.weather.WeatherDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class NumberRepository(private val number_service: NumberService) {

    // 전화번호 리스트
    val number_list : MutableLiveData<List<NumbersDTO>> = MutableLiveData()


    fun getNumberItemsByPaging() : Flow<PagingData<NumbersDTO>> {
        return Pager(
            config = PagingConfig(pageSize = 12),
            pagingSourceFactory = { NumberPagingSource(number_service) }
        ).flow
    }


}