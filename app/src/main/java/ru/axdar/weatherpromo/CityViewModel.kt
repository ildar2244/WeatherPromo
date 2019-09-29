package ru.axdar.weatherpromo

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.axdar.weatherpromo.local.CityDatabase
import ru.axdar.weatherpromo.local.CityEntity
import ru.axdar.weatherpromo.local.CityRepository
import ru.axdar.weatherpromo.network.WeatherRepository

/** Created by qq_3000 on 19.09.2019. */
class CityViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "CityViewModel"

    private val repoLocal: CityRepository
    private val repoNet: WeatherRepository = WeatherRepository()
    val allCities: LiveData<List<CityEntity>>

    init {
        val cityDao = CityDatabase.getDatabase(application, viewModelScope).cityDao()
        repoLocal = CityRepository(cityDao)
        allCities = repoLocal.allCities

        viewModelScope.launch(Dispatchers.IO) {
            loadWeather()
        }
    }

    private suspend fun loadWeather() {
        val list = repoLocal.listCities()
        Log.d(TAG, "LOAD: $list")
        for (item in list) {
            loadForCity(item)
        }
    }

    private suspend fun loadForCity(cityEntity: CityEntity) {
        val response = repoNet.loadWeather(cityEntity.name)
        val temp = response.list.first().main.temp
        val updateCity: CityEntity = cityEntity.copy(temperature = temp)
        Log.d(TAG, "UpdateCITY: $updateCity")
        updateTemperature(updateCity)
    }

    fun insertCity(cityEntity: CityEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repoLocal.insertRoom(cityEntity)
            val cityAdded = repoLocal.listCities().last()
            Log.d(TAG, "ADDED: $cityAdded")
            loadForCity(cityAdded)
        }
    }

    private suspend fun updateTemperature(cityEntity: CityEntity) {
        repoLocal.updateRoom(cityEntity)
        Log.d(TAG, "UPDATE: $cityEntity")
    }
}