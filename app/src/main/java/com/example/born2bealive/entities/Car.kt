package com.example.born2bealive.entities

data class Car(
    var id: Int,
    var name : String,
    var power_max : Int,
    var km_max : Int,
    var marque : String,
    var modele : String,
    var organisation : String,
    var immatriculation : String,

    var user : User
){
    constructor() : this(-1, "",-1,-1,"","","","",User())
}