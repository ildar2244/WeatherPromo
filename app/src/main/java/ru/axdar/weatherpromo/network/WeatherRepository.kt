package ru.axdar.weatherpromo.network

/** Created by qq_3000 on 20.09.2019. */
class WeatherRepository {
    var webservice : WeatherApi = RetrofitClient().connectWeatherApi

    suspend fun loadWeather(cityName: String) = webservice.getCityWeather(cityName)
}