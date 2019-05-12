package com.example.born2bealive.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.support.design.widget.BottomNavigationView
import com.example.born2bealive.R
import com.example.born2bealive.fragments.HomeFragment
import com.example.born2bealive.fragments.ProfileFragment
import com.example.born2bealive.fragments.ReservationCarFragment
import com.example.born2bealive.fragments.ReservationStationFragment



class BottomMenuActivity() : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_menu)

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(this)

        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setLogo(R.mipmap.logo_bornetobealive)
        supportActionBar!!.setDisplayUseLogoEnabled(true)

        loadFragment(HomeFragment())
    }


    fun loadFragment(fragment: Fragment?): Boolean {

        if (fragment != null) {

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            return true
        }
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        val fragActual = supportFragmentManager.findFragmentById(R.id.fragment_container)
        when (item.itemId) {
            R.id.navigation_profile -> if (fragActual !is ProfileFragment) {
                fragment = ProfileFragment()
                setTitle("Profil")
            }
            R.id.navigation_res_car -> if (fragActual !is ReservationCarFragment) {
                fragment = ReservationCarFragment()
                setTitle("Réservation de véhicule")
            }
            R.id.navigation_res_station -> if (fragActual !is ReservationStationFragment) {
                fragment = ReservationStationFragment()
                setTitle("Réservation de borne ")
            }
            R.id.navigation_home -> if (fragActual !is HomeFragment) {
                fragment = HomeFragment()
                setTitle("Accueil")

            }
            R.id.disconnect_menu -> {
            }

            else -> {
                fragment = HomeFragment()
                setTitle("Accueil")
            }
        }//Intent intent = new Intent(BottomMenuActivity.this, LoginActivity.class);
        //startActivity(intent);
        return loadFragment(fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.disconnect, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.disconnect_menu -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }// Intent intent = new Intent(BottomMenuActivity.this, LoginActivity.class);
        //startActivity(intent);
        return super.onOptionsItemSelected(item)
    }

    fun setTitle(title: String) {
        supportActionBar!!.title = Html.fromHtml("<font color='#008577'>$title </font>")
    }


}
