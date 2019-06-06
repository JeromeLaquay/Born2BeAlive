package com.example.born2bealive.resources

import com.example.born2bealive.entities.ReservationCar
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

public interface ReservationCarService{

    @POST("api/cars/reservations")
    fun create(@Body resCar : ReservationCar): Call<ReservationCar>

    @GET("api/cars/{id}/reservations")
    fun findByCar(@Path("id") idCar : Int): Call<List<ReservationCar>>

    @GET("api/users/{id}/cars/reservations")
    fun findByUser(@Path("id") idUser : Int): Call<List<ReservationCar>>

    @POST("/cars/{id}/reservations/existing")
    fun existingReservationWithinPeriod(@Path("id") idCar : Int, @Body resCar : ReservationCar): Call<Boolean>

}