package com.example.bodyboost

import android.app.ProgressDialog
import android.widget.Toast
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
                .baseUrl("https://df30-2401-e180-88a0-c467-ac89-72b0-857d-2ea8.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RetrofitAPI::class.java)
        }
        return retrofitAPI!!
    }

}

