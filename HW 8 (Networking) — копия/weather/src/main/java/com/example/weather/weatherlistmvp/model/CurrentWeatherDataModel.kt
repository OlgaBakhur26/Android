package com.example.weather.weatherlistmvp.model

data class CurrentWeatherDataModel(
        val temperature: String,
        val description: String,
        val icon: String,
        val city: String
)