package ru.axdar.weatherpromo.local

import androidx.lifecycle.LiveData
import kotlinx.coroutines.coroutineScope
import ru.axdar.weatherpromo.local.CityDao
import ru.axdar.weatherpromo.local.CityEntity

/** Created by qq_3000 on 19.09.2019. */
class CityRepository(private val cityDao: CityDao) {

    val allCities: LiveData<List<CityEntity>> = cityDao.getAllCity()

    fun listCities(): List<CityEntity> = cityDao.getCities()

    suspend fun insertRoom(cityEntity: CityEntity) {cityDao.insertCity(cityEntity)}

    suspend fun updateRoom(cityEntity: CityEntity) {cityDao.updateCity(cityEntity)}
}