package com.example.born2bealive.entities

data class User (

    var id: Int,
    var username: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var badge: String,
    var email: String,
    var confirmPassword: String

){
    constructor() : this(-1,"","","","","","","")
}
