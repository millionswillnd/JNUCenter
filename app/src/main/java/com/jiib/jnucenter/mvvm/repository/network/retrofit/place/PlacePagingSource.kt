package com.jiib.jnucenter.mvvm.repository.network.retrofit.place

import androidx.paging.PagingSource
import androidx.paging.PagingState


/**
 *   장소 목록 PagingSource
 */


private const val STARTING_INDEX = 1

class PlacePagingSource(
    private val place_service : PlaceService,
    private val search_words : String,
    private val isSearch : Boolean
) : PagingSource<Int, PlaceDTO>() {

    lateinit var places : List<PlaceDTO>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlaceDTO> {
        val page_index = params.key ?: STARTING_INDEX
        return try {
            // 검색 x. 전체 목록
            if(isSearch == false){
                places = place_service.getPlaces(page_index)
            }
            // 검색하는 경우
            else {
                places = place_service.getPlacesByName(search_words, page_index)
            }

            LoadResult.Page(
                data = places,
                prevKey = if (page_index == STARTING_INDEX) null else page_index,
                nextKey = page_index+1
            )
        } catch (e:Exception){
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PlaceDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }

  }
}