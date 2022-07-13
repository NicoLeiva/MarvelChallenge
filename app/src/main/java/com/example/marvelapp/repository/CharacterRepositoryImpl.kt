package com.example.marvelapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marvelapp.connection.RetroService
import com.example.marvelapp.model.CharacterData
import com.example.marvelapp.model.ResponseEvent
import com.example.marvelapp.ui.adapter.CharacterPagingSource
import com.example.marvelapp.ui.adapter.CharacterRemoteDataSourceImpl
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class CharacterRepositoryImpl(private val apiService: RetroService,
                              private val characterRemoteDataSourceImpl:CharacterRemoteDataSourceImpl): ICharacterRepository {

    override suspend fun getEventData() : Response<ResponseEvent> {
        return apiService.getEventListData()

    }

    override fun getCharacterData(): Flow<PagingData<CharacterData>> {
        return Pager (config = PagingConfig(pageSize = 15, maxSize = 200),
            pagingSourceFactory = { CharacterPagingSource(characterRemoteDataSourceImpl) }).flow
    }
}