package ru.axdar.weatherpromo.network

import retrofit2.http.GET
import retrofit2.http.Path

/** Created by qq_3000 on 20.09.2019. */
interface WeatherApi {

    @GET("find?q={city}&lang=ru&units=metric&appid=6bba77f5426e8a3ad2a7695cfbab47ad")
    suspend fun getCityWeather(@Path("city") city: String): WeatherData
}