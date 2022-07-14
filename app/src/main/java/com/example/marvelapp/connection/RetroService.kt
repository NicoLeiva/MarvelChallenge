package com.example.marvelapp.connection

import com.example.marvelapp.model.ResponseEvent
import com.example.marvelapp.model.ResponseList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

    @GET("characters")
    suspend fun getDataFromApi(@Query("offset") offset: Int?= 0): Response<ResponseList>

    @GET("events")
    suspend fun getEventListData(@Query("limit") limit: Int?= 25): Response<ResponseEvent>
}