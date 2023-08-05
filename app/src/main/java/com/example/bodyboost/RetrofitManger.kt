package com.example.bodyboost

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitManger private constructor() {
    private lateinit var retrofit: Retrofit
    private lateinit var retrofitAPI: RetrofitAPI

    private fun RetrofitManger() {
        retrofit = Retrofit.Builder()
            .baseUrl("127.0.0.1:8000/api")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitAPI = retrofit.create(RetrofitAPI::class.java)
    }


    companion object {
        private val manager = RetrofitManger()
        val getAPI: Retrofit
            get() = manager.retrofit
    }

}