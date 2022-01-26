package com.jiib.jnucenter.mvvm.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jiib.jnucenter.mvvm.repository.network.retrofit.numbers.NumberPagingSource
import com.jiib.jnucenter.mvvm.repository.network.retrofit.numbers.NumberService
import com.jiib.jnucenter.mvvm.repository.network.retrofit.numbers.NumbersDTO
import kotlinx.coroutines.flow.Flow

/**
 *   전화번호부 관련 Repository
 */

class NumberRepository(private val number_service: NumberService) {

    // 검색된 전화번호 리스트
    val search_number_list : MutableLiveData<List<NumbersDTO>> = MutableLiveData()


    // 전체 전화번호 리스트
    suspend fun getNumberItemsByPaging() : Flow<PagingData<NumbersDTO>> {
        return Pager(
            config = PagingConfig(pageSize = 12),
            pagingSourceFactory = { NumberPagingSource(number_service,"", "false") }
        ).flow
    }

    // 특정 검색어 포함 전화번호 리스트
    suspend fun getNumbersBySearch(search_name: String) : Flow<PagingData<NumbersDTO>>{
        return Pager(
            config = PagingConfig(pageSize = 12),
            pagingSourceFactory = {NumberPagingSource(number_service, search_name, "true")}
        ).flow
    }


}