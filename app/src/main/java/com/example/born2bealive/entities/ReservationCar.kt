package com.example.born2bealive.entities

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class ReservationCar(
    var id :Int,

    var date_start : Date,
    var date_end : Date,
    var user: User,
    var car : Car

){
    constructor() : this(0, Date(), Date(),User(),Car())

}