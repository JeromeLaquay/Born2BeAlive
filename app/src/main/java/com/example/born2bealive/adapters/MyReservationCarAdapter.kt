package com.example.born2bealive.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.born2bealive.R
import com.example.born2bealive.entities.ReservationCar
import com.example.born2bealive.resources.ResourceUtil
import kotlinx.android.synthetic.main.my_reservation_car_item.view.*

class MyReservationCarAdapter(val items: List<ReservationCar>, val context: Context?) : RecyclerView.Adapter<MyReservationCarAdapter.InfosHolder>() {

    var resourceUtil = ResourceUtil()

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReservationCarAdapter.InfosHolder {
        val inflater = LayoutInflater.from(parent.context)
        val inflatedView = inflater.inflate(R.layout.my_reservation_car_item,null, false)
        return InfosHolder(inflatedView)
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: MyReservationCarAdapter.InfosHolder, position: Int) {
        holder.date_end.text = resourceUtil.dateToString(resourceUtil.addTwoHours(items.get(position).date_end))
        holder.date_start.text = resourceUtil.dateToString(resourceUtil.addTwoHours(items.get(position).date_start))
        holder.vehicule.text = items.get(position).car.marque + " "+ items.get(position).car.modele + " "+ items.get(position).car.immatriculation
    }

    class InfosHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val date_end = view.date_end
        val date_start = view.date_start
        val vehicule = view.vehicule

    }
}