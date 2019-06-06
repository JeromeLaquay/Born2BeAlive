package com.example.born2bealive.resources

import android.R
import android.app.Activity
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TimePicker
import java.text.SimpleDateFormat
import java.util.*


public class ResourceUtil{


    /**
     * get date from datePicker with format 2019-07-01T12:00:00.000+0000
     */
    fun getDateFromDateTimePicker(datePicker : DatePicker, timePicker: TimePicker, hourPlus : Int) : Date {
        var date: Date
        val year = datePicker.year-1900
        val hour = timePicker.hour+hourPlus
        if(hour > 24){
            var dateToday = Date(year, datePicker.month, datePicker.dayOfMonth, timePicker.hour, timePicker.minute)
            println("jerome laquay : "+ dateToday.toString())
            date = Date(dateToday.time + (1000*60*60*hourPlus))
            println("jerome laquay : "+ date.toString())
        }else{
            date= Date(year, datePicker.month, datePicker.dayOfMonth, hour, timePicker.minute)
        }
        return date
    }


    fun dateToString(date: Date) : String{
        val sServerDateDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
        return sServerDateDateFormat.format(date)
    }


    /**
     * fill in a spinner with an arraylist of strings
     */
    fun fillInSpinner(activity : Activity, spinner : Spinner, list : ArrayList<String>){
        val adapter = ArrayAdapter(activity, R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    fun estimateTimeForCompleteCharge(powerCar : Int, typeChargeStation : Double, autonomy : Int) : String{
        val power : Double
        var hour : Int
        var minute : Int
        power = (((100-(autonomy.toDouble()))/100)*(powerCar.toDouble()))

        hour =  (power/typeChargeStation).toInt()
        minute = (((power%typeChargeStation)/typeChargeStation)*60).toInt()

        return hour.toString()+" h "+minute.toString()
    }

    fun addTwoHours(date : Date) : Date{
        return Date(date.time + (1000*60*60*2))
    }
}