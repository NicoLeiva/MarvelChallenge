package com.example.marvelapp.repository

import androidx.paging.PagingData
import com.example.marvelapp.model.ResponseEvent
import com.example.marvelapp.model.CharacterData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ICharacterRepository {
    suspend fun getEventData(): Response<ResponseEvent>
    fun getCharacterData(): Flow<PagingData<CharacterData>>
}