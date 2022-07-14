package com.example.marvelapp.connection

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetworkingModules {
    private const val baseUrl = "https://gateway.marvel.com/v1/public/"

    private val okHttpClientModule = module {
        single {
            OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    var original = chain.request()
                    val url = original.url().newBuilder()
                        .addQueryParameter("apikey", "3a783b25c80e1c44875356dd363f272d")
                        .addQueryParameter("hash", "51a3ecf2f92a23817992a2663183325e")
                        .addQueryParameter("ts", "1")
                        .build()
                    original = original.newBuilder().url(url).build()
                    return@Interceptor chain.proceed(original)
                }).build()
        }
    }
    private val retrofitModule = module {
        single {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
    private val serviceModule = module{
        factory {
            this.get<Retrofit>().create<RetroService>()
        }
    }
    val all = arrayOf(okHttpClientModule, retrofitModule, serviceModule)
}