package com.example.born2bealive.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.born2bealive.R
import com.example.born2bealive.adapters.MyReservationCarAdapter
import com.example.born2bealive.entities.ReservationCar
import com.example.born2bealive.entities.User
import com.example.born2bealive.managers.UserManager
import com.example.born2bealive.resources.RetrofitFactory
import kotlinx.android.synthetic.main.activity_my_reservation_car.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyReservationCarActivity : AppCompatActivity() {

    lateinit var reservations : List<ReservationCar>
    var currentUser = User()

    var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_reservation_car)
        supportActionBar!!.hide()

        currentUser = UserManager.instance(this).getUser()
        token = UserManager.instance(this).getToken()

        getReservationCarByUser()

    }

    fun displayInfos(){
        runOnUiThread {
            infos_liste.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,true)
            infos_liste.adapter = MyReservationCarAdapter(reservations, this)
        }
    }

    fun getReservationCarByUser(){
        val service = RetrofitFactory.reservationCarServiceWithToken(token)
        val call = service.findByUser(currentUser.id)
        call.enqueue(object: Callback<List<ReservationCar>> {

            override fun onResponse(call: Call<List<ReservationCar>>, response: Response<List<ReservationCar>>) {

                if(response.isSuccessful){
                        reservations = response.body()!!
                        displayInfos()
                }else {
                    Toast.makeText(this@MyReservationCarActivity, "pseudo ou mot de passe incorrect", Toast.LENGTH_LONG).show()
                }

            }
            override fun onFailure(call: Call<List<ReservationCar>>, t: Throwable) {
                Toast.makeText(this@MyReservationCarActivity, "pseudo ou mot de passe incorrect", Toast.LENGTH_LONG).show()
            }
        })
    }

}