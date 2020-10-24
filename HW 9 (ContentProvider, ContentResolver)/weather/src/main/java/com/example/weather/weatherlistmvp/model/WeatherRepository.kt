package com.example.weather.weatherlistmvp.model

import io.reactivex.Single

interface WeatherRepository {
    fun getWeatherList(city: String, unit: String): Single<List<WeatherListDataModel>>
    fun getCurrentWeather(city: String, unit: String): Single<CurrentWeatherDataModel>
}