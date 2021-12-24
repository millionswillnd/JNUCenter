package com.jiib.jnucenter.mvvm.feature.number

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jiib.jnucenter.mvvm.repository.NumberRepository
import com.jiib.jnucenter.mvvm.repository.network.retrofit.numbers.NumberService
import com.jiib.jnucenter.mvvm.repository.network.retrofit.numbers.NumbersDTO
import kotlinx.coroutines.flow.Flow

class NumberViewModel() : ViewModel() {

    private val number_repository = NumberRepository(NumberService())

    val number_list : MutableLiveData<List<NumbersDTO>>
        get() = number_repository.search_number_list

    // 전화번호 리스트 get
    suspend fun getNumbers(): Flow<PagingData<NumbersDTO>>{
        return number_repository.getNumberItemsByPaging()
            .cachedIn(viewModelScope)
    }

    // 검색 부서명 포함 전화번호 리스트 get
    suspend fun getNumbersBySearch(search_name : String): Flow<PagingData<NumbersDTO>>{
        return number_repository.getNumbersBySearch(search_name)
            .cachedIn(viewModelScope)
    }

}