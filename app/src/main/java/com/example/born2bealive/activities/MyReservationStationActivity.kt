package com.example.born2bealive.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.born2bealive.R
import com.example.born2bealive.adapters.MyReservationCarAdapter
import com.example.born2bealive.adapters.MyReservationStationAdapter
import com.example.born2bealive.entities.ReservationCar
import com.example.born2bealive.entities.ReservationStation
import com.example.born2bealive.entities.User
import com.example.born2bealive.managers.UserManager
import com.example.born2bealive.resources.RetrofitFactory
import kotlinx.android.synthetic.main.activity_my_reservation_car.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyReservationStationActivity : AppCompatActivity() {

    lateinit var reservations : List<ReservationStation>
    var currentUser = User()

    var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_reservation_station)
        supportActionBar!!.hide()

        currentUser = UserManager.instance(this).getUser()
        token = UserManager.instance(this).getToken()

        getReservationCarByUser()

    }

    fun displayInfos(){
        runOnUiThread {
            infos_liste.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,true)
            infos_liste.adapter = MyReservationStationAdapter(reservations, this)
        }
    }

    fun getReservationCarByUser(){
        val service = RetrofitFactory.reservationStationServiceWithToken(token)
        val call = service.findByUser(currentUser.id)
        call.enqueue(object: Callback<List<ReservationStation>> {

            override fun onResponse(call: Call<List<ReservationStation>>, response: Response<List<ReservationStation>>) {

                if(response.isSuccessful){
                        reservations = response.body()!!
                        displayInfos()
                }else {
                    Toast.makeText(this@MyReservationStationActivity, "pseudo ou mot de passe incorrect", Toast.LENGTH_LONG).show()
                }

            }
            override fun onFailure(call: Call<List<ReservationStation>>, t: Throwable) {
                Toast.makeText(this@MyReservationStationActivity, "pseudo ou mot de passe incorrect", Toast.LENGTH_LONG).show()
            }
        })
    }

}