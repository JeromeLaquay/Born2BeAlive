package com.example.born2bealive.resources

import com.example.born2bealive.entities.ReservationStation
import com.example.born2bealive.entities.User
import com.example.born2bealive.entities.UserToken
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

public interface ReservationStationService{

    @POST("api/stations/reservations")
    fun create(@Body resStation : ReservationStation): Call<ReservationStation>

    @GET("api/stations/{id}/reservations")
    fun findByStation(@Path("id") idStation : Int): Call<List<ReservationStation>>

    @GET("api/users/{id}/stations/reservations")
    fun findByUser(@Path("id") idUser : Int): Call<List<ReservationStation>>

    @GET("api/stations/{id}/reservations/existing")
    fun existingReservationWithinPeriod(@Path("id") idStation : Int, @Body resStation : ReservationStation): Call<Boolean>

}