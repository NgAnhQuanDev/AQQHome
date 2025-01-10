package com.example.aqqhome.utils

import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.utils.utils.url2
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = url2

    val instance: ApiAQQHome by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiAQQHome::class.java)
    }
}
