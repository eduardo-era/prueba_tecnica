package com.example.myapplication.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiServiceProvider {
    fun <Api> buildApiService(
        apiInterface: Class<Api>,
        baseUrl: String,
        timeout: Long
    ): Api {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getOkHttpClient(timeout).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiInterface)
    }

    private fun getOkHttpClient(timeout: Long): OkHttpClient.Builder =
        OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            )
}