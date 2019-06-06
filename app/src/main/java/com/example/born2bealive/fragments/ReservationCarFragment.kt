package com.example.born2bealive.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.born2bealive.R
import com.example.born2bealive.entities.Car
import com.example.born2bealive.entities.ReservationCar
import com.example.born2bealive.entities.User
import com.example.born2bealive.managers.UserManager
import com.example.born2bealive.resources.ResourceUtil
import com.example.born2bealive.resources.RetrofitFactory
import kotlinx.android.synthetic.main.reservation_car_dialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ReservationCarFragment : Fragment() {

    /**
     * Reservation of a car
     */
    var reservationCar = ReservationCar()
    var currentUser = User()
    /**
     * composants
     */
    lateinit var datePicker : DatePicker
    lateinit var timePicker : TimePicker
    lateinit var buttonMinus : Button
    lateinit var buttonPlus  : Button
    lateinit var numberHourTxt : TextView
    lateinit var buttonMinusKm : Button
    lateinit var buttonPlusKm  : Button
    lateinit var kmTxt : TextView
    lateinit var buttonChooseCar  : Button

    lateinit var spinner : Spinner


    var resourceUtil = ResourceUtil()

    var token = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_res_car, null)

        currentUser = UserManager.instance(activity!!).getUser()
        token = UserManager.instance(activity!!).getToken()
        reservationCar.user = currentUser

        /**
         * initialisation components
         */
        datePicker  = view.findViewById<DatePicker>(R.id.datePickerCar)
        timePicker  = view.findViewById<TimePicker>(R.id.timePickerCar)
        buttonMinus  = view.findViewById<Button>(R.id.buttonMinusHour)
        buttonPlus  = view.findViewById<Button>(R.id.buttonPlusHour)
        buttonMinusKm  = view.findViewById<Button>(R.id.buttonMinusKm)
        buttonPlusKm  = view.findViewById<Button>(R.id.buttonPlusKm)

        numberHourTxt  = view.findViewById<TextView>(R.id.numberHour)
        kmTxt  = view.findViewById<TextView>(R.id.km)
        buttonChooseCar  = view.findViewById<Button>(R.id.btnChooseCar)


        /**
         * -1 to hour
         */
        buttonMinus.setOnClickListener(){
            val number = numberHourTxt.text.toString().toInt();
            if(number > 0)  numberHourTxt.text = (number - 1).toString();

        }
        /**
         * +1 to hour
         */
        buttonPlus.setOnClickListener() {
            val number = numberHourTxt.text.toString().toInt();
            numberHourTxt.text = (number + 1).toString();
        }
        /**
         * -10 to kms
         */
        buttonMinusKm.setOnClickListener(){
            val number = kmTxt.text.toString().toInt();
            if(number > 0) kmTxt.text = (number - 10).toString();
        }
        /**
         * +10 to kms
         */
        buttonPlusKm.setOnClickListener() {
            val number = kmTxt.text.toString().toInt();
            kmTxt.text = (number + 10).toString();
        }

        /**
         * validate date_start, time_start, number of kms and hours and find available cars
         */
        buttonChooseCar.setOnClickListener {
            if(numberHourTxt.text.toString().toInt() == 0 || kmTxt.text.toString().toInt() ==0){
                Toast.makeText(activity, "Veuillez renseigner le nombre de kms et d'heures",Toast.LENGTH_LONG).show()
            }else{
                openChooseCarDialog()
            }

        }

        return view
    }

    /**
     * get number of kms and hours , and date_start like "2019-05-16 19:33:00"
     */
    fun findAvailableCarsWithCriteria( personal : Boolean){
        val km = kmTxt.text.toString().toInt()
        reservationCar.date_start = resourceUtil.getDateFromDateTimePicker(datePicker,timePicker,0)
        reservationCar.date_end = resourceUtil.getDateFromDateTimePicker(datePicker,timePicker,numberHourTxt.text.toString().toInt())

        var carFreeStrings =  ArrayList<String>()
        println("jeromelaquay : "+ reservationCar.date_start.toString())
        val service = RetrofitFactory.carServiceWithToken(token)
        val call = service!!.getAllCarssFreeWithinPeriod(reservationCar)
        call.enqueue(object: Callback<List<Car>> {

            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {

                if(response.isSuccessful){
                    val freeCars = response.body()!!
                    if(!freeCars.isEmpty()) {
                        for (car in freeCars) {
                            if (car.km_max > km ) {
                                if(car.user != null){
                                    if(personal && car.user.id == currentUser.id){
                                        carFreeStrings.add(car.marque + " " + car.modele + " " + car.immatriculation)
                                    }
                                }else if(!personal){
                                    carFreeStrings.add(car.marque + " " + car.modele + " " + car.immatriculation)
                                }
                            }
                        }
                        resourceUtil.fillInSpinner(activity!!, spinner, carFreeStrings)

                    }else{
                        Toast.makeText(activity, "Aucun véhicule disponible avec ces critères", Toast.LENGTH_LONG).show()
                    }

                }else {
                    Toast.makeText(activity, "impossible de récupérer les véhicules libres", Toast.LENGTH_LONG).show()
                }

            }
            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(activity, "impossible de récupérer les véhicules libres", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun openChooseCarDialog(){
        println("jerome laquay :"  + datePicker.year + " "+ datePicker.month+" "+datePicker.dayOfMonth + " "+ timePicker.hour+ " "+timePicker.minute)
        /*reservationCar.date_start = resourceUtil.transformStringToDate(resourceUtil.getStringFromDateTimePicker(datePicker, timePicker,0))
        reservationCar.date_end = resourceUtil.transformStringToDate(resourceUtil.getStringFromDateTimePicker(datePicker, timePicker,numberHourTxt.text.toString().toInt()))*/
        reservationCar.date_start = resourceUtil.getDateFromDateTimePicker(datePicker,timePicker,0)
        reservationCar.date_end = resourceUtil.getDateFromDateTimePicker(datePicker,timePicker,numberHourTxt.text.toString().toInt())
        reservationCar.user = currentUser

        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.reservation_car_dialog, null)
        /**
         * initialisation components from alert dialog
         */
        val dateStartTxt  = mDialogView.findViewById<TextView>(R.id.dateStart)
        val dateEndTxt  = mDialogView.findViewById<TextView>(R.id.dateEnd)
        val togglePersonal = mDialogView.findViewById<ToggleButton>(R.id.togglePersonal)
        val btnReserve  = mDialogView.findViewById<Button>(R.id.dialogReserveBtn)
        val btnCancel  = mDialogView.findViewById<Button>(R.id.dialogCancelBtn)
        val nbrekmTxt = mDialogView.findViewById<TextView>(R.id.nbreKm)
        spinner = mDialogView.findViewById<Spinner>(R.id.spinner1)

        dateStartTxt.text=resourceUtil.dateToString(reservationCar.date_start)
        dateEndTxt.text=resourceUtil.dateToString(reservationCar.date_end)
        nbrekmTxt.text = kmTxt.text.toString()

        val mBuilder = AlertDialog.Builder(activity!!)
            .setView(mDialogView)
        val  mAlertDialog = mBuilder.show()

        findAvailableCarsWithCriteria(false)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val itemSelected = p0?.getItemAtPosition(p2).toString()
                findByImmatriculation(itemSelected)
            }
        }

        togglePersonal.setOnClickListener {
            if(togglePersonal.isChecked){
                findAvailableCarsWithCriteria(true)
            }else{
                findAvailableCarsWithCriteria(false)
            }

        }

        btnReserve.setOnClickListener {
            createReservation()
            mAlertDialog.dismiss()
        }
        btnCancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    fun findByImmatriculation(itemSelected : String){
        val cutChooseCar = itemSelected.split(" ")
        println("jeromelaquay : "+ cutChooseCar[2])
        val service = RetrofitFactory.carServiceWithToken(token)
        val call = service!!.findByImmatriculation(cutChooseCar[2])
        call.enqueue(object: Callback<Car> {

            override fun onResponse(call: Call<Car>, response: Response<Car>) {

                if(response.isSuccessful){
                    reservationCar.car = response.body()!!
                }else {
                    Toast.makeText(activity, "car not found", Toast.LENGTH_LONG).show()
                }

            }
            override fun onFailure(call: Call<Car>, t: Throwable) {
            }
        })

    }

    fun createReservation(){
        if(reservationCar.date_start != Date() && reservationCar.date_end != Date() && reservationCar.user != User() && reservationCar.car != Car()){
            val service = RetrofitFactory.reservationCarServiceWithToken(token)
            val call = service!!.create(reservationCar)
            call.enqueue(object: Callback<ReservationCar>{

                override fun onResponse(call: Call<ReservationCar>, response: Response<ReservationCar>) {

                    if(response.isSuccessful){
                        Toast.makeText(activity, "Réservation effectuée", Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(activity, "reservation non créée", Toast.LENGTH_LONG).show()
                    }

                }
                override fun onFailure(call: Call<ReservationCar>, t: Throwable) {
                    Toast.makeText(activity, "reservation non créée 2", Toast.LENGTH_LONG).show()
                }
            })
        }
        else{
            Toast.makeText(activity, "un champ est vide", Toast.LENGTH_LONG).show()
        }

    }

}