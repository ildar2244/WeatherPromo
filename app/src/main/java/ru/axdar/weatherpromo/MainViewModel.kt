package ru.axdar.weatherpromo

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.axdar.weatherpromo.local.CityDatabase
import ru.axdar.weatherpromo.local.CityEntity
import ru.axdar.weatherpromo.local.CityRepository
import ru.axdar.weatherpromo.network.WeatherRepository
import java.net.UnknownHostException

/** Created by qq_3000 on 19.09.2019. */
class MainViewModel(application: Application) : AndroidViewModel(application) {
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
        for (item in list) {
            loadForCity(item)
        }
    }

    private suspend fun loadForCity(cityEntity: CityEntity) {
        try {
            val response = repoNet.loadWeather(cityEntity.name)
            val temp = response.list.first().main.temp
            val updateCity: CityEntity = cityEntity.copy(temperature = temp)
            updateTemperature(updateCity)
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> {//при отсутствии подключения к сети
                    //что-то делаем
                }
                is NoSuchElementException -> {//при отсутствии данных по названию этого города
                    //что-то делаем
                }
                else -> deleteCity(cityEntity)
            }
        }
    }

    fun insertCity(cityEntity: CityEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repoLocal.insertRoom(cityEntity)
            val cityAdded = repoLocal.listCities().last()
            loadForCity(cityAdded)
        }
    }

    private suspend fun updateTemperature(cityEntity: CityEntity) {
        repoLocal.updateRoom(cityEntity)
    }

    private suspend fun deleteCity(cityEntity: CityEntity) {
        repoLocal.deleteRoom(cityEntity)
    }
}