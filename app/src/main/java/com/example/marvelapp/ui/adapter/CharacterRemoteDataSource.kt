package com.example.marvelapp.ui.adapter

import com.example.marvelapp.connection.RetroService
import com.example.marvelapp.model.CharacterData

class CharacterRemoteDataSourceImpl(private val apiService: RetroService){

    suspend fun getAllCharacter(offset: Int): Result<Pair<Int, List<CharacterData>>> {
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