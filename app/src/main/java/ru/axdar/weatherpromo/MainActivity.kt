package ru.axdar.weatherpromo

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

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var cityViewModel: CityViewModel
    private var cityList: MutableList<CityEntity> = mutableListOf<CityEntity>()

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
//                cityList = cities.toMutableList()
//                Log.d(TAG, "onCreate: mLIST: $cityList")
                getApiData(cities)
            }
        })
    }

    private fun getApiData(list: List<CityEntity>?) {
        if (list!!.isNotEmpty()) {
            for (item in list) {
                cityViewModel.loadWeather(item.name).observe(this, Observer {
                    val temp = it.list.first().main.temp
                    val updateCity: CityEntity = item.copy(temperature = temp)
                    Log.d(TAG, "getApiData: TEMP: $updateCity")
//                    cityViewModel.updateTemperature(updateCity)
                })
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_city -> {
                //code for add new city
                Toast.makeText(this, "Please enter city name.", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
