package com.example.born2bealive.entities

data class Car(
    var id: Integer,
    var name : String,
    var power : String,
    var marque : String,
    var organisation : String,

    var station : Station,

    var user : User,
    var reservationCarList : List<ReservationCar>
)