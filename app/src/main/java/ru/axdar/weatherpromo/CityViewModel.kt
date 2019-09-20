package ru.axdar.weatherpromo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import ru.axdar.weatherpromo.local.CityDatabase
import ru.axdar.weatherpromo.local.CityEntity
import ru.axdar.weatherpromo.local.CityRepository
import ru.axdar.weatherpromo.network.WeatherRepository

/** Created by qq_3000 on 19.09.2019. */
class CityViewModel(application: Application) : AndroidViewModel(application) {

    private val repoLocal: CityRepository
    private val repoNet: WeatherRepository = WeatherRepository()
    val allCities: LiveData<List<CityEntity>>

    init {
        val cityDao = CityDatabase.buildDatabase(application, viewModelScope).cityDao()
        repoLocal = CityRepository(cityDao)
        allCities = repoLocal.allCities
    }

    fun loadWeather(city: String) = liveData(Dispatchers.IO) {
        val response = repoNet.loadWeather(city)
        emit(response)
    }
}