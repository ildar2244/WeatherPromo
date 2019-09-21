package ru.axdar.weatherpromo.network

import com.google.gson.annotations.SerializedName

/** Created by qq_3000 on 20.09.2019. */
data class WeatherData(@SerializedName("list") val list: List<MainData>)

data class MainData(@SerializedName("main") val main: TemperatureData)

data class TemperatureData(@SerializedName("temp") val temp: Double)