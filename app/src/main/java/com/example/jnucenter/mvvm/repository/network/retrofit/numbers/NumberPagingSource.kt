package com.example.jnucenter.mvvm.repository.network.retrofit.numbers

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.lang.Exception

private const val STARTING_INDEX = 1
private const val LOAD_ITEM_NUMBERS = 12

class NumberPagingSource(
    private val number_service : NumberService,
    private val search_words : String,
    private val isSearch : String
): PagingSource<Int, NumbersDTO>() {

    lateinit var numbers : List<NumbersDTO>

    // 반환형에 2번째 value 데이터타입은 List로 넣는거아님
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NumbersDTO> {
        // 현재 페이지 idx
        val page_index = params.key ?: STARTING_INDEX
        return try {

            if (isSearch == "false"){
                numbers = number_service.getNumbers(page_index)!!
            }
            // 검색하는 경우
            else {
                numbers = number_service.getNumbersBySearch(search_words, page_index)!!
            }

            LoadResult.Page(
                data = numbers,
                prevKey = if(page_index == STARTING_INDEX) null else page_index,
                nextKey = page_index+1
            )
        } catch (e:Exception){
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