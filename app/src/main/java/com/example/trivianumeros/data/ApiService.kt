package com.example.trivianumeros.data


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.converter.scalars.ScalarsConverterFactory


interface ApiService {
    @GET("{number}")
    suspend fun getTrivia(
    @Path("number") number: String
    ): String
}

object RetrofitServiceFactory{
    fun makeRetrofitService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("http://numbersapi.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }
}
