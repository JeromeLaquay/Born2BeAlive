package com.example.born2bealive.managers

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.example.born2bealive.entities.User
import com.example.born2bealive.resources.RetrofitFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public object UserManager{

        const val TOKEN_KEY = "token"
        const val USER_KEY = "user"
        const val SHARED_PREFERENCES_KEY = "borntobealive"
         lateinit var mSharedPreferences: SharedPreferences

         operator fun invoke(context: Context): UserManager {

             return this
         }

         fun instance(context: Context): UserManager {
             mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY,0)
             return this
         }

         fun setToken(token : String){
             val pref = mSharedPreferences.edit()
             pref.putString(TOKEN_KEY,token).apply()
         }

         fun getToken(): String{
             val pref = mSharedPreferences
             return pref.getString(TOKEN_KEY, "")
         }

         fun setUser(user : User){
             val pref = mSharedPreferences.edit()
             pref.putString(USER_KEY,Gson().toJson(user)).apply()
         }

         fun getUser(): User{

             val user =  mSharedPreferences.getString(USER_KEY, "")
             return Gson().fromJson(user,User::class.java)
         }

}