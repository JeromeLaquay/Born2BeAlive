package com.example.born2bealive.entities

data class Station(
     var id: Int,
     var name : String,
     var power : String,
     var marque : String,
     var organisation : String

){
     constructor() : this(-1,"","","","")
}