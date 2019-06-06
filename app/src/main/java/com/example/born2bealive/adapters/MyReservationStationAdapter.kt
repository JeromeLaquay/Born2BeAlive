package com.example.born2bealive.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.born2bealive.R
import com.example.born2bealive.entities.ReservationCar
import com.example.born2bealive.entities.ReservationStation
import com.example.born2bealive.resources.ResourceUtil
import kotlinx.android.synthetic.main.my_reservation_car_item.view.*
import kotlinx.android.synthetic.main.my_reservation_car_item.view.date_end
import kotlinx.android.synthetic.main.my_reservation_car_item.view.date_start
import kotlinx.android.synthetic.main.my_reservation_car_item.view.vehicule
import kotlinx.android.synthetic.main.my_reservation_station_item.view.*

class MyReservationStationAdapter(val items: List<ReservationStation>, val context: Context?) : RecyclerView.Adapter<MyReservationStationAdapter.InfosHolder>() {

    var resourceUtil = ResourceUtil()

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReservationStationAdapter.InfosHolder {
        val inflater = LayoutInflater.from(parent.context)
        val inflatedView = inflater.inflate(R.layout.my_reservation_station_item,null, false)
        return InfosHolder(inflatedView)
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: MyReservationStationAdapter.InfosHolder, position: Int) {
        holder.date_end.text = resourceUtil.dateToString(resourceUtil.addTwoHours(items.get(position).date_end))
        holder.date_start.text = resourceUtil.dateToString(resourceUtil.addTwoHours(items.get(position).date_start))
        holder.vehicule.text = items.get(position).car.marque + " "+ items.get(position).car.modele + " "+ items.get(position).car.immatriculation
        holder.station.text = items.get(position).station.name
    }

    class InfosHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val date_end = view.date_end
        val date_start = view.date_start
        val vehicule = view.vehicule
        val station = view.station

    }
}