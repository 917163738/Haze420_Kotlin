package com.haze420.android.webservice.core

import com.haze420.android.webservice.HomeService
import com.haze420.android.webservice.OnboardingService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitFactory {
    private const val BASE_URL = "http://api.haze420.co.uk:8880/api/mobile/v1/"
    private const val READ_TIME_OUT_LIMIT: Long = 10
    private const val CONNECT_TIME_OUT_LIMIT: Long = 10

    fun makeOnboardingService(): OnboardingService {
        return createRetrofitClient().create(OnboardingService::class.java)
    }

    fun makeHomeServiceService(token: String): HomeService {
        return createRetrofitClientWithToken(token).create(HomeService::class.java)
    }

    private fun createRetrofitClient(): Retrofit{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(READ_TIME_OUT_LIMIT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIME_OUT_LIMIT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(noAuthHeaderIntercepter())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    private fun createRetrofitClientWithToken(token: String): Retrofit{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(READ_TIME_OUT_LIMIT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIME_OUT_LIMIT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(authHeaderIntercepter(token))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    private fun noAuthHeaderIntercepter() = Interceptor { chain ->
        val headerValue = "XMLHttpRequest"

        chain.proceed(chain.request().newBuilder()
            .addHeader("X-Requested-With", headerValue)
            .build())
    }

    private fun authHeaderIntercepter(token: String) = Interceptor { chain ->
        val headerValue = "XMLHttpRequest"

        chain.proceed(chain.request().newBuilder()
            .addHeader("X-Requested-With", headerValue)
            .addHeader("Authorization", token)
            .build())
    }
}