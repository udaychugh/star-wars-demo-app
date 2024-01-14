package com.assesment.groww.data.cloud.api

import android.os.Build
import com.assesment.groww.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CloudAPIClient {

    private var instance: Retrofit? = null

    private lateinit var baseUrl: String

    private fun addLoggingInterceptor(builder: OkHttpClient.Builder) {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        builder.addInterceptor(loggingInterceptor)
    }

    fun initializeWith(baseUrl: String) {
        CloudAPIClient.baseUrl = baseUrl
    }

    fun getInstance(): Retrofit {
        if (instance == null) {
            val httpClientBuilder = OkHttpClient.Builder()
            httpClientBuilder.addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .addHeader("x-app-os-name", "android")
                        .addHeader("x-app-os-version", "v${Build.VERSION.RELEASE} (${Build.VERSION.SDK_INT})")
                        .build()
                )
            }

            httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
            httpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
            httpClientBuilder.writeTimeout(1, TimeUnit.MINUTES)

            addLoggingInterceptor(httpClientBuilder)

            instance = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return instance!!
    }

}