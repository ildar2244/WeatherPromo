package ru.axdar.weatherpromo.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/** Created by qq_3000 on 19.09.2019. */
@Entity(tableName = "city_table")
data class CityEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "city") val name: String,
    @ColumnInfo(name = "temperature") var temperature: Double = 0.0
)