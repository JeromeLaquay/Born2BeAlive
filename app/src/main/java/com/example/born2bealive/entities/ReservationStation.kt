package com.example.born2bealive.entities

import java.sql.Timestamp
import java.util.*

data class ReservationStation(
    var id :Int,
    var date_start : Date,
    var date_end : Date,
    var user: User,
    var station : Station,
    var car : Car
) {
    constructor() : this(-1,Date(), Date(),User(),Station(),Car())
}