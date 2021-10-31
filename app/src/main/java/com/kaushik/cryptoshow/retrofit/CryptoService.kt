package com.kaushik.cryptoshow.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface CryptoService {
    @Headers("x-rapidapi-host:coingecko.p.rapidapi.com",
        "x-rapidapi-key:045587a4edmshe2a59e5da916c86p168af4jsnc03d8b191ed6")
    @GET("/coins/markets")
    fun getCrypto(@QueryMap filter: HashMap<String,String>): Call<ArrayList<Crypto>>

}