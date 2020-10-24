package com.example.weather.weatherlistmvp.presenter

import com.example.weather.weatherlistmvp.model.CurrentWeatherDataModel
import com.example.weather.weatherlistmvp.view.CurrentWeatherDataModelUI

class CurrentWeatherDataModelUIMapper: (CurrentWeatherDataModel) -> CurrentWeatherDataModelUI {
    override fun invoke(currentWeatherDataModel: CurrentWeatherDataModel): CurrentWeatherDataModelUI =
            CurrentWeatherDataModelUI(
                    temperature = currentWeatherDataModel.temperature,
                    description = currentWeatherDataModel.description,
                    icon = currentWeatherDataModel.icon,
                    city = currentWeatherDataModel.city
            )
}