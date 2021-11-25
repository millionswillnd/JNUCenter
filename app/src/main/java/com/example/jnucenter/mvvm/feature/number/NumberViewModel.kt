package com.example.jnucenter.mvvm.feature.number

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jnucenter.mvvm.repository.NumberRepository
import com.example.jnucenter.mvvm.repository.Repository
import com.example.jnucenter.mvvm.repository.network.retrofit.numbers.NumberService
import com.example.jnucenter.mvvm.repository.network.retrofit.numbers.NumbersDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NumberViewModel() : ViewModel() {

    private val number_repository = NumberRepository(NumberService())

    suspend fun getNumbers(): Flow<PagingData<NumbersDTO>>{

        return number_repository.getNumberItemsByPaging()
            .cachedIn(viewModelScope)
    }

}