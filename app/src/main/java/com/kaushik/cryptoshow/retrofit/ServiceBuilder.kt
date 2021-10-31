package com.kaushik.cryptoshow.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    // Base URL
    private const val URL = "https://coingecko.p.rapidapi.com"

    // logger // interceptor
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // okHttp client
    private val okHttp = OkHttpClient.Builder().addInterceptor(logger)

    // Retrofit Builder
    private val builder = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttp.build())

    // Retrofit Instance
    private val retrofit = builder.build()

    // Service Builder
    fun <T>buildService(serviceType: Class<T>): T{
        return retrofit.create(serviceType)
    }
}