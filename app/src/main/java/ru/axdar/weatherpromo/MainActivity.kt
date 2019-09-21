package ru.axdar.weatherpromo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.axdar.weatherpromo.local.CityEntity

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var cityViewModel: CityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CityListAdapter(this)
        cities_recycler.adapter = adapter
        cities_recycler.layoutManager = LinearLayoutManager(this)

        cityViewModel = ViewModelProviders.of(this).get(CityViewModel::class.java)

        cityViewModel.allCities.observe(this, Observer { cities ->
            cities?.let {
                adapter.setCityList(it)
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
                    Log.d(TAG, "onCreate: TEMP: $updateCity")
                })
            }
        }
    }
}
