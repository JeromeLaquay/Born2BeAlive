package com.example.born2bealive.entities

data class UserToken(

    var access_token : String,
    var token_type : String,
    var expires_in : Integer,
    var scope : String,
    var jti : String

)