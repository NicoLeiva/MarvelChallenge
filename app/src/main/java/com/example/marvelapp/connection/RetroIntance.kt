package com.example.marvelapp.connection

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object {
        //private const val baseUrl = "https://rickandmortyapi.com/api/"
        private const val baseUrl = "https://gateway.marvel.com/v1/public/"
        fun getRetroInstance(): Retrofit {

            val client = OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    var original = chain.request()
                    val url = original.url().newBuilder()
                        .addQueryParameter("apikey", "3a783b25c80e1c44875356dd363f272d")
                        .addQueryParameter("hash", "51a3ecf2f92a23817992a2663183325e")
                        .addQueryParameter("ts", "1")
                        .build()
                    original = original.newBuilder().url(url).build()
                    return@Interceptor chain.proceed(original)
                })
                .build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }


    }

}