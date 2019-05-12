package com.example.born2bealive.entities

import java.util.*

data class ReservationCar(
    var id :Integer,
    var date_start : Date,
    var date_end : Date,
    var user: User,
    var car : Car

)