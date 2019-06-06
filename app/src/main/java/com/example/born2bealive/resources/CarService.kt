package com.example.born2bealive.resources

import com.example.born2bealive.entities.Car
import com.example.born2bealive.entities.ReservationCar
import retrofit2.Call
import retrofit2.http.*
import java.util.*


public interface CarService{

    @POST("api/cars")
    fun create(@Body car : Car): Call<Car>

    @GET("api/users/{id}/cars")
    fun findByUser(@Path("id") idUser : Integer): Call<List<Car>>

    @GET("api/cars")
    fun getAllCars(): Call<List<Car>>

    @GET("api/cars/without_user")
    fun findWithoutUser(): Call<Boolean>

    @GET("api/cars/immatriculation")
    fun findByImmatriculation(@Query("immatriculation") immatriculation : String): Call<Car>


    @POST("api/cars/free")
    fun getAllCarssFreeWithinPeriod( @Body resCar : ReservationCar): Call<List<Car>>


    @POST("api/cars/{id}/free")
    fun existingReservationForOneCar( @Path("id") idCar : Int, @Body resCar : ReservationCar): Call<Boolean>


}