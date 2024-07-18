package com.example.core.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class Retrofit() {
    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }

    val retrofit get() = initRetrofit()

    @Suppress("UnstableApiUsage")
    private fun initRetrofit(): Retrofit {
        val httpClient = OkHttpClient().newBuilder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
        try {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(interceptor)
        }
        catch (e: Exception) {
            print("failed to add firebase analytic interceptor OKHTTP")
        }

        val okHttpClient = httpClient.build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> create(retrofitInterface: Class<T>): T {
        return retrofit.create(retrofitInterface)
    }
}