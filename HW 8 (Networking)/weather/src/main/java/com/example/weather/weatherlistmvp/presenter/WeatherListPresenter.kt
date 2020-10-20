package com.example.weather.weatherlistmvp.presenter

interface WeatherListPresenter {
    fun fetchWeatherList(cityName: String, unit: String)
    fun fetchCurrentWeather(cityName: String, unit: String)
    fun dispose()
}