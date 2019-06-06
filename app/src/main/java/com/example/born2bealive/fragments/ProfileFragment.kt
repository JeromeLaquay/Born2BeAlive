package com.example.born2bealive.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.born2bealive.R
import com.example.born2bealive.activities.MyReservationCarActivity
import com.example.born2bealive.activities.MyReservationStationActivity
import com.example.born2bealive.entities.User
import com.example.born2bealive.managers.UserManager
import com.example.born2bealive.resources.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by JEROMELaquay on 17/03/2018.
 */

class ProfileFragment : Fragment() {

    lateinit var mReservationCar  : Button
    lateinit var mReservationStation  : Button
    var currentUser = User()
    var token = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_profile, null)
        currentUser = UserManager.instance(activity!!).getUser()
        token = UserManager.instance(activity!!).getToken()

        mReservationCar        = view.findViewById<Button>(R.id.reservationCar)
        mReservationStation        = view.findViewById<Button>(R.id.reservationStation)

        mReservationCar.setOnClickListener {
            val intent = Intent(activity, MyReservationCarActivity::class.java)
            startActivity(intent)
        }

        mReservationStation.setOnClickListener {
            val intent = Intent(activity, MyReservationStationActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}
