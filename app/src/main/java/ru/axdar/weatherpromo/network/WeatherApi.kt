package ru.axdar.weatherpromo.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/** Created by qq_3000 on 20.09.2019. */
interface WeatherApi {

    @GET("find?lang=ru&units=metric&appid=6d393d190c001a40dd135becaa0327f2")
    suspend fun getCityWeather(@Query("q") city: String): WeatherData
}