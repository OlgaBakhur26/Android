package com.example.weather.weatherlistmvp.view

interface WeatherListView {
    fun showWeatherList(weatherListDataModelUI: List<WeatherListDataModelUI>)
    fun showCurrentWeather(currentWeatherDataModelUI: CurrentWeatherDataModelUI)
    fun onErrorWeatherList(errorMessage: String)
    fun onErrorCurrentWeather(errorMessage: String)
}