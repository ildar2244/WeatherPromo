package ru.axdar.weatherpromo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope

/** Created by qq_3000 on 19.09.2019. */
class CityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CityRepository

    val allCities: LiveData<List<CityEntity>>

    init {
        val cityDao = CityDatabase.buildDatabase(application, viewModelScope).cityDao()
        repository = CityRepository(cityDao)
        allCities = repository.allCities
    }
}