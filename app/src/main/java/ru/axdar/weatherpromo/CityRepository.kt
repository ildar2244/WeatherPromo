package ru.axdar.weatherpromo

import androidx.lifecycle.LiveData

/** Created by qq_3000 on 19.09.2019. */
class CityRepository(private val cityDao: CityDao) {

    val allCities: LiveData<List<CityEntity>> = cityDao.getAllCity()
}