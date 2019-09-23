package ru.axdar.weatherpromo.local

import androidx.lifecycle.LiveData
import androidx.room.*

/** Created by qq_3000 on 19.09.2019. */
@Dao
interface CityDao {

    @Query("SELECT * FROM city_table")
    fun getAllCity(): LiveData<List<CityEntity>>

    @Insert
    suspend fun insertCity(city: CityEntity)

    @Update
    suspend fun updateCity(city: CityEntity)

    @Query("UPDATE city_table SET temperature = :temp WHERE id = :id")
    suspend fun updateTempCity(temp: Double, id: Int)

    @Delete
    suspend fun deleteCity(city: CityEntity)

    @Query("DELETE FROM city_table")
    suspend fun deleteAll()
}