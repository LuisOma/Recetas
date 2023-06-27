package com.example.recipes.data.network.retrofit

import com.google.gson.GsonBuilder
import com.example.recipes.data.BuildConfig
import com.example.recipes.data.BuildConfig.BASE_URL
import com.example.recipes.data.network.NetworkSettings
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object {

        private val client: OkHttpClient = OkHttpClient.Builder().apply {
            readTimeout(NetworkSettings.READ_TIME_OUT, TimeUnit.SECONDS)
            connectTimeout(NetworkSettings.CONNECT_TIME_OUT, TimeUnit.SECONDS)

            addInterceptor(GeneralInterceptor())
            if (BuildConfig.BUILD_TYPE == "debug") addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })

        }.build()

        fun getInstance(): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(client)
            .build()
    }

    private class GeneralInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()
            val response = chain.proceed(newRequestWithAccessToken(request))
            return response
            return chain.proceed(request)
        }

        private fun newRequestWithAccessToken(request: Request): Request =
            request.newBuilder()
                .build()
    }
}