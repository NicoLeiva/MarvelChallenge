package com.example.marvelapp.ui.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelapp.connection.RetroService
import com.example.marvelapp.model.CharacterData
import java.lang.Exception

class CharacterPagingSource(private val apiService: RetroService): PagingSource<Int, CharacterData>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterData> {
        return try {
            val nextPage: Int = params.key ?: 0
            val response = getData(nextPage).getOrNull()



            LoadResult.Page(data = response?.second ?: emptyList(),
            prevKey = null,
            nextKey = response?.first,
            )
        }
        catch (e: Exception){
            LoadResult.Error(e)
        }
    }

   private suspend fun getData(offset:Int):Result<Pair<Int, List<CharacterData>>>{
       return try {
           val response = apiService.getDataFromApi(offset)
           if (response.isSuccessful) {
               val nextOffset = response.body()?.data?.offset?.plus(20) ?: offset
               val characterList = response.body()?.data?.results
               Result.success(Pair(nextOffset, characterList ?: emptyList()))
           } else {
               throw IllegalStateException(response.message())
           }
       } catch (t: Throwable) {
           Result.failure(t)
       }
    }
}