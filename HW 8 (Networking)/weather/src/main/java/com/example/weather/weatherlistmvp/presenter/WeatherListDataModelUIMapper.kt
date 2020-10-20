package com.example.weather.weatherlistmvp.presenter

import com.example.weather.weatherlistmvp.model.WeatherListDataModel
import com.example.weather.weatherlistmvp.view.WeatherListDataModelUI

class WeatherListDataModelUIMapper: (List<WeatherListDataModel>) -> List<WeatherListDataModelUI> {
    override fun invoke(weatherListDataModel: List<WeatherListDataModel>): List<WeatherListDataModelUI> =
        weatherListDataModel.map { weatherListDataModel ->
            WeatherListDataModelUI(
                    time = weatherListDataModel.time,
                    temperature = weatherListDataModel.temperature,
                    description = weatherListDataModel.description,
                    icon = weatherListDataModel.icon
            )
        }

}