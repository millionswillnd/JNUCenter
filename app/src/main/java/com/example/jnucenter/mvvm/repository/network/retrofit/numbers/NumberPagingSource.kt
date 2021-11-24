package com.example.jnucenter.mvvm.repository.network.retrofit.numbers

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.lang.Exception

private const val STARTING_INDEX = 1
private const val LOAD_ITEM_NUMBERS = 12

class NumberPagingSource(
    private val number_service : NumberService
): PagingSource<Int, NumbersDTO>() {

    // 반환형에 2번째 value 데이터타입은 List로 넣는거아님
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NumbersDTO> {

        Log.d("테스트", " 1")
        // 현재 페이지 idx
        val page_index = params.key ?: STARTING_INDEX
        return try {
            val numbers : List<NumbersDTO> = number_service.getNumbers(page_index)!!

            // 다음에 로드할 페이지 idx
            val next_key = if(numbers.isEmpty()){
                null
            } else {
                page_index + (params.loadSize/ LOAD_ITEM_NUMBERS)
            }

            LoadResult.Page(
                data = numbers,
                prevKey = if(page_index == STARTING_INDEX) null else page_index,  // 첫 인덱스라면 prevKey가 필요 없음
                nextKey = next_key
            )
        } catch (e:Exception){
            Log.d("테스트", "3")
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NumbersDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}