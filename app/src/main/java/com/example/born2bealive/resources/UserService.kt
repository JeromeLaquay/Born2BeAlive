package com.example.born2bealive.resources

import com.example.born2bealive.entities.User
import com.example.born2bealive.entities.UserToken
import retrofit2.Call
import retrofit2.http.*

public interface UserService{
    @FormUrlEncoded
    @POST("oauth/token")
    fun login(@Field("username") username : String, @Field("password") password : String,@Field("grant_type") grant_type : String): Call<UserToken>


    @POST("api/register")
    fun register(@Body user : User): Call<User>

    @GET("api/users/current")
    fun currentUser(): Call<User>

}