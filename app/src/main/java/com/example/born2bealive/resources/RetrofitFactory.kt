package com.example.born2bealive.resources

import com.example.born2bealive.BuildConfig
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.example.born2bealive.resources.UserService
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory{
    const val authentication_url = "https://born-to-be-alive-api.herokuapp.com/"

    fun makeRetrofitService() : UserService {
        val client = OkHttpClient.Builder()
        client
            .addInterceptor(LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.NONE)
            .log(Platform.INFO)
            .request("Request")
            .response("Response")
            .addHeader("Authorization", Credentials.basic("borntobealiveclientid","btbeacs135792468"))
            .build())
        val okHttpClient = client.build()

        return Retrofit.Builder()
            .baseUrl(authentication_url)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build().create(UserService::class.java)
    }


}