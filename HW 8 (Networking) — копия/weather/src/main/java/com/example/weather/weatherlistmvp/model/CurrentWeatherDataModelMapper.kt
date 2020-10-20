package com.example.weather.weatherlistmvp.model

import org.json.JSONObject

class CurrentWeatherDataModelMapper : (String) -> CurrentWeatherDataModel {

    override fun invoke(jsonData: String): CurrentWeatherDataModel {
        val jsonObject = JSONObject(jsonData)
        val jsonWeatherArray = jsonObject.getJSONArray("weather")
        val jsonMainArray = jsonObject.getJSONObject("main")

        val weatherMainViewDataModel = CurrentWeatherDataModel(
                temperature = jsonMainArray.getString("temp"),
                description = jsonWeatherArray.getJSONObject(0).getString("main"),
                icon = jsonWeatherArray.getJSONObject(0).getString("icon"),
                city = jsonObject.getString("name")
        )
        return weatherMainViewDataModel
    }
}