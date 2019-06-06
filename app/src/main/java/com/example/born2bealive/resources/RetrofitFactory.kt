package com.example.born2bealive.resources

import com.example.born2bealive.BuildConfig
import com.google.gson.*
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import com.google.gson.GsonBuilder
import com.google.gson.Gson



object RetrofitFactory {
    const val authentication_url = "https://born-to-be-alive-api.herokuapp.com/"

    fun makeRetrofitService(): UserService {
        val client = OkHttpClient.Builder()
        client
            .addInterceptor(
                LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.NONE)
                    .log(Platform.INFO)
                    .request("Request")
                    .response("Response")
                    .addHeader("Authorization", Credentials.basic("borntobealiveclientid", "btbeacs135792468"))
                    .build()
            )
        val okHttpClient = client.build()

        return Retrofit.Builder()
            .baseUrl(authentication_url)
            .addConverterFactory(GsonConverterFactory.create(gsonWithDate()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build().create(UserService::class.java)
    }

    fun userServiceWithToken(token: String): UserService {
        val client = OkHttpClient.Builder()
        client
            .addInterceptor(
                LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.NONE)
                    .log(Platform.INFO)
                    .request("Request")
                    .response("Response")
                    .addHeader("Authorization", Credentials.basic("borntobealiveclientid", "btbeacs135792468"))
                    .addHeader("Authorization", "bearer " + token)
                    .build()
            )
        val okHttpClient = client.build()

        return Retrofit.Builder()
            .baseUrl(authentication_url)
            .addConverterFactory(GsonConverterFactory.create(gsonWithDate()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build().create(UserService::class.java)
    }

    fun reservationStationServiceWithToken(token: String): ReservationStationService {
        val client = OkHttpClient.Builder()
        client
            .addInterceptor(
                LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.NONE)
                    .log(Platform.INFO)
                    .request("Request")
                    .response("Response")
                    .addHeader("Authorization", Credentials.basic("borntobealiveclientid", "btbeacs135792468"))
                    .addHeader("Authorization", "bearer " + token)
                    .build()
            )
        val okHttpClient = client.build()

        return Retrofit.Builder()
            .baseUrl(authentication_url)
            .addConverterFactory(GsonConverterFactory.create(gsonWithDate()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build().create(ReservationStationService::class.java)
    }

    fun carServiceWithToken(token: String): CarService {
        val client = OkHttpClient.Builder()
        client
            .addInterceptor(
                LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.NONE)
                    .log(Platform.INFO)
                    .request("Request")
                    .response("Response")
                    .addHeader("Authorization", Credentials.basic("borntobealiveclientid", "btbeacs135792468"))
                    .addHeader("Authorization", "bearer " + token)
                    .build()
            )
        val okHttpClient = client.build()

        return Retrofit.Builder()
            .baseUrl(authentication_url)
            .addConverterFactory(GsonConverterFactory.create(gsonWithDate()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build().create(CarService::class.java)
    }

    fun stationServiceWithToken(token: String): StationService {
        val client = OkHttpClient.Builder()
        client
            .addInterceptor(
                LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.NONE)
                    .log(Platform.INFO)
                    .request("Request")
                    .response("Response")
                    .addHeader("Authorization", Credentials.basic("borntobealiveclientid", "btbeacs135792468"))
                    .addHeader("Authorization", "bearer " + token)
                    .build()
            )
        val okHttpClient = client.build()

        return Retrofit.Builder()
            .baseUrl(authentication_url)
            .addConverterFactory(GsonConverterFactory.create(gsonWithDate()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build().create(StationService::class.java)
    }

    fun reservationCarServiceWithToken(token: String): ReservationCarService {
        val client = OkHttpClient.Builder()
        client
            .addInterceptor(
                LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.NONE)
                    .log(Platform.INFO)
                    .request("Request")
                    .response("Response")
                    .addHeader("Authorization", Credentials.basic("borntobealiveclientid", "btbeacs135792468"))
                    .addHeader("Authorization", "bearer " + token)
                    .build()
            )
        val okHttpClient = client.build()

        return Retrofit.Builder()
            .baseUrl(authentication_url)
            .addConverterFactory(GsonConverterFactory.create(gsonWithDate()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
            .create(ReservationCarService::class.java)
    }


    fun gsonWithDate(): Gson {
        val builder = GsonBuilder()
            .registerTypeAdapter(Date::class.java, DateDeserializer())
            .registerTypeAdapter(Date::class.java, DateSerializer())
            .create()
        return builder
    }
}