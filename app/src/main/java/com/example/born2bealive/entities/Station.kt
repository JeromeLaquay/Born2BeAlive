package com.example.born2bealive.entities

data class Station(
     var id: Integer,
     var name : String,
     var power : String,
     var marque : String,
     var organisation : String,

     var car : Car,

     var reservationStationList : List<ReservationStation>
)