package com.example.born2bealive.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import com.example.born2bealive.R
import kotlinx.android.synthetic.main.fragment_res_car.*

class ReservationCarFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_res_car, null)

        val buttonMinus  = view.findViewById<Button>(R.id.buttonMinusHour)
        buttonMinus.setOnClickListener(){
            val number = numberHour.text.toString().toInt();
            if(number > 0) {
                numberHour.text = (number - 1).toString();
            }
        }

        val buttonPlus  = view.findViewById<Button>(R.id.buttonPlusHour)
        buttonPlus.setOnClickListener() {
            val number = numberHour.text.toString().toInt();
            numberHour.text = (number + 1).toString();
        }

        val buttonMinusKm  = view.findViewById<Button>(R.id.buttonMinusKm)
        buttonMinusKm.setOnClickListener(){
            val number = km.text.toString().toInt();
            if(number > 0) {
                km.text = (number - 10).toString();
            }
        }

        val buttonPlusKm  = view.findViewById<Button>(R.id.buttonPlusKm)
        buttonPlusKm.setOnClickListener() {
            val number = km.text.toString().toInt();
            km.text = (number + 10).toString();
        }

        val spinner = view.findViewById<Spinner>(R.id.spinner1)
        //item selected listener for spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                station.text = ""
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val itemSelected = p0?.getItemAtPosition(p2).toString()
                println("itemSelected : "+ itemSelected)
                var stationText =""
                when(itemSelected){
                    "Renault Zoé 25-BMZ-98" -> stationText = "Rizomm n°3"
                    "BMW i5 36-MLQ-12" +
                            "" +
                            "" -> stationText = "BD n°1"
                    "Tesla 15-PEN-03" -> stationText = "Rizomm n°2"
                    "Choisissez le véhicule" -> stationText = ""

                }
                station.text = stationText
            }
        }
        return view
    }
}
