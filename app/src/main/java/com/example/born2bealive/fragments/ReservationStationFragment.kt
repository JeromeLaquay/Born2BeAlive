package com.example.born2bealive.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatRadioButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.born2bealive.R
import com.example.born2bealive.entities.*
import com.example.born2bealive.resources.ResourceUtil
import com.example.born2bealive.resources.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.RadioGroup
import com.example.born2bealive.managers.UserManager
import java.util.*


/**
 * Created by JEROMELaquay on 17/03/2018.
 */

class ReservationStationFragment : Fragment(){

    var reservationStation : ReservationStation = ReservationStation()
    var currentUser = User()
    lateinit var token : String
    var carReservation = Car()

    var powerCharge = 0.0

    var resourceUtil = ResourceUtil()

    /*
     * components
     */
     lateinit var mButtonSetTime  : Button
     lateinit var mDatePicker : DatePicker
     lateinit var mTimePicker : TimePicker
     lateinit var mSpinnerCar : Spinner
     lateinit var mAutonomy : TextView
     lateinit var mButtonMinusautonomy  : Button
     lateinit var mButtonPlusautonomy   : Button
     lateinit var mRadioGroup: RadioGroup
    lateinit var mSevenPointFour : AppCompatRadioButton
    lateinit var mEleven : AppCompatRadioButton
    lateinit var mTwentyTwo : AppCompatRadioButton

    var carStringList = ArrayList<String>()

    /****************************************
     * Create View
     * launchs when the fragments starts
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_res_station, null)

        currentUser = UserManager.instance(activity!!).getUser()
        token = UserManager.instance(activity!!).getToken()
        reservationStation.user = currentUser

        /*
         * initialisation components
         */
        mDatePicker              = view.findViewById(R.id.simpleDatePicker)
        mTimePicker              = view.findViewById<TimePicker>(R.id.timePicker)
        mSpinnerCar              = view.findViewById<Spinner>(R.id.spinnerCar)
        mAutonomy            = view.findViewById<TextView>(R.id.autonomy)
        mButtonMinusautonomy        = view.findViewById<Button>(R.id.buttonMinusAutonomy)
        mButtonPlusautonomy         = view.findViewById<Button>(R.id.buttonPlusAutonomy)
        mRadioGroup          =view.findViewById<RadioGroup>(R.id.radioGroupCharge)
        mSevenPointFour             = view.findViewById<AppCompatRadioButton>(R.id.sevenPointFourKW)
        mEleven             = view.findViewById<AppCompatRadioButton>(R.id.elevenKW)
        mTwentyTwo             = view.findViewById<AppCompatRadioButton>(R.id.twentytwoKW)
        mButtonSetTime          =view.findViewById<Button>(R.id.buttonSetTime)

        getAllCars()

        /*
         * click for buttons minus and plus, choose for spinner and radio buttons
         */
        mButtonMinusautonomy.setOnClickListener(){
            val number = mAutonomy.text.toString().toInt();
            if(number > 0) mAutonomy.text = (number - 10).toString();
        }
        mButtonPlusautonomy.setOnClickListener() {
            val number = mAutonomy.text.toString().toInt();
            if(number < 100) mAutonomy.text = (number + 10).toString();
        }

        mButtonSetTime.setOnClickListener {
            openDialogTime()
        }

        mSpinnerCar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val itemSelected = p0?.getItemAtPosition(p2).toString()
                findByImmatriculation(itemSelected)
            }
        }

        mRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.sevenPointFourKW -> { powerCharge=7.4
                }
                R.id.elevenKW -> { powerCharge = 11.0
                }
                R.id.twentytwoKW -> { powerCharge = 22.0
                }
            }
        }

        return view
    }

    /**
     * End create View
     ***************************************/

    /****************************************
     * openDialogs
     */

    fun openDialogTime(){
        reservationStation.date_start = resourceUtil.getDateFromDateTimePicker(mDatePicker,mTimePicker,0)
        if(powerCharge == 0.0 || carReservation.immatriculation == "" || mAutonomy.text.toString().toInt() == 100){
            Toast.makeText(activity,"sélectionner un type de charge et une autonomie inférieur à 100%",Toast.LENGTH_LONG).show()
        }else {
            val mDialogView =
                LayoutInflater.from(activity).inflate(R.layout.reservation_station_estimated_time_dialog, null)

            /*
         * initialisation components from alert dialog
         */

            val mEstimation = mDialogView.findViewById<TextView>(R.id.estimation)
            val mNumberHour = mDialogView.findViewById<TextView>(R.id.numberHour)
            val mbButtonMinusHour = mDialogView.findViewById<Button>(R.id.buttonMinusHour)
            val mButtonPlusHour = mDialogView.findViewById<Button>(R.id.buttonPlusHour)
            val mDialogCancelBtn = mDialogView.findViewById<Button>(R.id.dialogCancelBtn)
            val mDialogCheckTimeBtn = mDialogView.findViewById<Button>(R.id.dialogCheckTimeBtn)

            val mBuilder = AlertDialog.Builder(activity!!)
                .setView(mDialogView)
            var mAlertDialog = mBuilder.show()

            /**
             * -1 to hour
             */
            mbButtonMinusHour.setOnClickListener(){
                val number = mNumberHour.text.toString().toInt()
                if(number > 1)  mNumberHour.text = (number - 1).toString()
            }
            /**
             * +1 to hour
             */
            mButtonPlusHour.setOnClickListener() {
                val number = mNumberHour.text.toString().toInt()
                mNumberHour.text = (number + 1).toString()
            }

            mDialogCancelBtn.setOnClickListener {
                mAlertDialog.dismiss()
            }

            mDialogCheckTimeBtn.setOnClickListener {

                if(mNumberHour.text.toString().toInt() > 0 ){
                    reservationStation.date_end = resourceUtil.getDateFromDateTimePicker(mDatePicker,mTimePicker,mNumberHour.text.toString().toInt())
                    carFree(mNumberHour.text.toString().toInt())

                }else{
                    Toast.makeText(activity,"Veuillez mettre un nombre d'heures supérieur à 0 ", Toast.LENGTH_LONG).show()
                }

            }

            mEstimation.text = resourceUtil.estimateTimeForCompleteCharge(
                carReservation.power_max,
                powerCharge,
                mAutonomy.text.toString().toInt()
            )
        }

    }

    fun openDialogChooseStation(){

        val mViewDialog = LayoutInflater.from(activity).inflate(R.layout.reservation_station_choose_station_dialog, null)

        val mDateDebutTxt = mViewDialog.findViewById<TextView>(R.id.dateStart)
        val mDateEndTxt = mViewDialog.findViewById<TextView>(R.id.dateEnd)
        val mCarChooseTxt = mViewDialog.findViewById<TextView>(R.id.carChooseText)
        val mTypeChargeTxt = mViewDialog.findViewById<TextView>(R.id.typeChargeText)
        val mSpinnerStation = mViewDialog.findViewById<Spinner>(R.id.spinnerStation)
        val mDialogCancelBtn = mViewDialog.findViewById<Button>(R.id.dialogCancelBtn)
        val mDialogReserveBtn = mViewDialog.findViewById<Button>(R.id.dialogReserveBtn)

        mDateDebutTxt.text = resourceUtil.dateToString(reservationStation.date_start)
        mDateEndTxt.text= resourceUtil.dateToString(reservationStation.date_end)
        mCarChooseTxt.text=reservationStation.car.marque+ " "+reservationStation.car.modele+ " "+ reservationStation.car.immatriculation
        mTypeChargeTxt.text=powerCharge.toString()
        findStationWithinPeriod(mSpinnerStation)

        val mBuilder = AlertDialog.Builder(activity!!)
            .setView(mViewDialog)
        val mDialogStation = mBuilder.show()

        mDialogCancelBtn.setOnClickListener(){
            mDialogStation.dismiss()
        }

        mDialogReserveBtn.setOnClickListener(){
            createReservationStation()
            mDialogStation.dismiss()
        }

        mSpinnerStation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val itemSelected = p0?.getItemAtPosition(p2).toString()
                findByName(itemSelected)
            }
        }

    }

    /**
     * End openDialogs
     ***************************************/

    /****************************************
     * requests Retrofit
     */

    /*
     * get free cars for first drop down list
     * fill in carStringList
     */
    fun getAllCars() {
        carStringList =  ArrayList<String>()
        val service = RetrofitFactory.carServiceWithToken(token)
        val call = service!!.getAllCars()
        call.enqueue(object: Callback<List<Car>> {

            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if(response.isSuccessful){
                    val freeCars = response.body()!!
                    for(car in freeCars){
                        carStringList.add(car.marque+ " "+ car.modele+ " "+ car.immatriculation)
                    }
                    resourceUtil.fillInSpinner(activity!!, mSpinnerCar, carStringList)

                }else {
                    Toast.makeText(activity, "impossible de récupérer les véhicules libres", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(activity, "impossible de récupérer les véhicules libres", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun findByImmatriculation(itemSelected : String){
        val cutChooseCar = itemSelected.split(" ")
        println("jeromelaquay : "+ cutChooseCar[2])
        val service = RetrofitFactory.carServiceWithToken(token)
        val call = service!!.findByImmatriculation(cutChooseCar[2])
        call.enqueue(object: Callback<Car> {

            override fun onResponse(call: Call<Car>, response: Response<Car>) {

                if(response.isSuccessful){
                    carReservation = response.body()!!
                    reservationStation.car = carReservation
                }else {
                    Toast.makeText(activity, "car not found", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Car>, t: Throwable) {
            }
        })
    }

    fun findByName(itemSelected : String){
        val service = RetrofitFactory.stationServiceWithToken(token)
        val call = service!!.findByName(itemSelected)
        call.enqueue(object: Callback<Station> {

            override fun onResponse(call: Call<Station>, response: Response<Station>) {

                if(response.isSuccessful){
                    val stationChoose = response.body()!!
                    reservationStation.station= stationChoose
                }else {
                    Toast.makeText(activity, "station not found", Toast.LENGTH_LONG).show()
                }

            }
            override fun onFailure(call: Call<Station>, t: Throwable) {
            }
        })

    }

    fun findStationWithinPeriod(spinner : Spinner){
        var stationsFree : List<Car>
        val stationStringList =  ArrayList<String>()
        val service = RetrofitFactory.stationServiceWithToken(token)
        val call = service!!.getAllStationsFreeWithinPeriod(reservationStation)
        call.enqueue(object: Callback<List<Station>> {

            override fun onResponse(call: Call<List<Station>>, response: Response<List<Station>>) {

                if(response.isSuccessful){
                    val stationsFreeList = response.body()!!
                    for(station in stationsFreeList){
                        stationStringList.add(station.name)
                    }
                    resourceUtil.fillInSpinner(activity!!, spinner, stationStringList)
                }else {
                    Toast.makeText(activity, "station not found", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<List<Station>>, t: Throwable) {
            }
        })
    }

    fun carFree(hour : Int){
        var ok = false
        reservationStation.date_start = resourceUtil.getDateFromDateTimePicker(mDatePicker,mTimePicker,0)
        reservationStation.date_end = resourceUtil.getDateFromDateTimePicker(mDatePicker,mTimePicker,hour)
        var resCar = ReservationCar()
        resCar.date_start = reservationStation.date_start
        resCar.date_end = reservationStation.date_end
        val service = RetrofitFactory.carServiceWithToken(token)
        val call = service!!.existingReservationForOneCar(reservationStation.car.id,resCar)
        call.enqueue(object: Callback<Boolean> {

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {

                if(response.isSuccessful){
                    val bool = response.body()!!
                    if(!bool) {
                        openDialogChooseStation()

                    }else{
                        Toast.makeText(activity, "Ce véhicule est déja réservé à un moment de la période choisie", Toast.LENGTH_LONG).show()
                    }

                }else {
                    Toast.makeText(activity, "impossible de récupérer les réservations", Toast.LENGTH_LONG).show()
                }

            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(activity, "impossible de récupérer les réservations", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun createReservationStation(){
        if(reservationStation.date_start != Date() && reservationStation.date_end != Date() && reservationStation.user != User() && reservationStation.car != Car() && reservationStation.station != Station()){
            val service = RetrofitFactory.reservationStationServiceWithToken(token)
            val call = service!!.create(reservationStation)
            call.enqueue(object: Callback<ReservationStation>{

                override fun onResponse(call: Call<ReservationStation>, response: Response<ReservationStation>) {

                    if(response.isSuccessful){
                        Toast.makeText(activity, "Réservation effectuée", Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(activity, "reservation non créée", Toast.LENGTH_LONG).show()
                    }

                }
                override fun onFailure(call: Call<ReservationStation>, t: Throwable) {
                    Toast.makeText(activity, "reservation non créée 2", Toast.LENGTH_LONG).show()
                }
            })
        }else{
            Toast.makeText(activity, "station null", Toast.LENGTH_LONG).show()
        }
    }
    /**
     * End requests Retrofit
     ***************************************/

}
