package com.playground.myattendance.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private fun getRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.179.97/")
//            .baseUrl(("http://192.168.0.107/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance() : ApiClient {
        return getRetrofitClient().create(ApiClient::class.java)
    }
}