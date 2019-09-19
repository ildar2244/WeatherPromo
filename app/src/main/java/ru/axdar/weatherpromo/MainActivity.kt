package ru.axdar.weatherpromo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var cityViewModel: CityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CityListAdapter(this)
        cities_recycler.adapter = adapter
        cities_recycler.layoutManager = LinearLayoutManager(this)

        cityViewModel = ViewModelProviders.of(this).get(CityViewModel::class.java)
        cityViewModel.allCities.observe(this, Observer { cities ->
            cities?.let { adapter.setCityList(it) }
        })
    }
}
