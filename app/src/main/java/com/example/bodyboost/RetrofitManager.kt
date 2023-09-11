package com.example.bodyboost

import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitManager {
    private var retrofitAPI: RetrofitAPI? = null

    fun getInstance(): RetrofitAPI {
        if (retrofitAPI == null) {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .header("Authorization", Credentials.basic("xting", "kth5yg1y1"))
                        .method(original.method(), original.body())
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                .build()

            retrofitAPI = Retrofit.Builder()
                .baseUrl("https://2d5d-2401-e180-8d6c-432c-75ed-2936-f519-a717.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RetrofitAPI::class.java)
        }
        return retrofitAPI!!
    }

}

