package ru.axdar.weatherpromo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.axdar.weatherpromo.local.CityEntity

class MainActivity : AppCompatActivity(), NewCityDialog.Listener {
    private val TAG = "MainActivity"
    private lateinit var cityViewModel: CityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.toolbar_title)

        val adapter = CityListAdapter(this)
        cities_recycler.adapter = adapter
        cities_recycler.layoutManager = LinearLayoutManager(this)

        cityViewModel = ViewModelProviders.of(this).get(CityViewModel::class.java)

        cityViewModel.allCities.observe(this, Observer { cities ->
            cities?.let {
                adapter.setCityList(it)
                Log.d(TAG, "onCreate: OBSERVE: $it")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_city -> {
                NewCityDialog().show(supportFragmentManager, "city_dialog")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onInputCityConfirm(cityName: String) {
        if (cityName.isNotEmpty()) {
            val newCity = CityEntity(name = cityName)
            cityViewModel.insertCity(newCity)
        } else {
            Toast.makeText(this, "Необходимо ввести название города",
                Toast.LENGTH_SHORT).show()
        }
    }
}
