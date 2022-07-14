package com.example.marvelapp.ui.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelapp.model.CharacterData
import java.lang.Exception

class CharacterPagingSource(private val characterRemoteDataSourceImpl:CharacterRemoteDataSource): PagingSource<Int, CharacterData>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterData> {
        return try {
            val nextPage: Int = params.key ?: 0
            val response = characterRemoteDataSourceImpl.getAllCharacter(nextPage).getOrNull()

            LoadResult.Page(data = response?.second ?: emptyList(),
            prevKey = null,
            nextKey = response?.first,
            )
        }
        catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}