package com.example.born2bealive.resources

import com.example.born2bealive.entities.ReservationStation
import com.example.born2bealive.entities.Station
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

public interface StationService{

    @POST("/api/stations/free")
    fun getAllStationsFreeWithinPeriod(@Body resStation : ReservationStation): Call<List<Station>>

    @GET("api/stations/name")
    fun findByName(@Query("name") name : String): Call<Station>
}