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

class MainActivity : AppCompatActivity(), NewCityDialog.Listener {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.toolbar_title)

        val adapter = CityListAdapter(this)
        cities_recycler.adapter = adapter
        cities_recycler.layoutManager = LinearLayoutManager(this)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        mainViewModel.allCities.observe(this, Observer { cities ->
            cities?.let {
                adapter.setCityList(it)
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
            mainViewModel.insertCity(newCity)
        } else {
            Toast.makeText(this, "Необходимо ввести название города",
                Toast.LENGTH_SHORT).show()
        }
    }
}
