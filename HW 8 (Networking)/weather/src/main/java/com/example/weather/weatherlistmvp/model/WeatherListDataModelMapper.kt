package com.example.weather.weatherlistmvp.model

import org.json.JSONObject

class WeatherListDataModelMapper : (String) -> List<WeatherListDataModel> {

    override fun invoke(jsonData: String): List<WeatherListDataModel> {
        val jsonObject = JSONObject(jsonData)
        val jsonListArray = jsonObject.getJSONArray("list")
        val itemList = mutableListOf<WeatherListDataModel>()

        if (jsonListArray.length() > 0) {
            for (index in 0 until jsonListArray.length()) {
                val jsonListItem = jsonListArray.getJSONObject(index)
                val weatherDataModel = WeatherListDataModel(
                        time = jsonListItem.getString("dt_txt").substring(11, 16),
                        temperature = jsonListItem.getJSONObject("main").getInt("temp").toString(),
                        description = jsonListItem.getJSONArray("weather").getJSONObject(0).getString("main"),
                        icon = jsonListItem.getJSONArray("weather").getJSONObject(0).getString("icon")
                )
                itemList.add(weatherDataModel)
            }
            return itemList
        }
        return emptyList()
    }
}