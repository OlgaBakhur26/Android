package com.example.weather.citieslistmvvm.view

import com.example.weather.citieslistmvvm.model.CityDataModel

interface OnCityClickListener {
    fun onCityClick(cityDataModel: CityDataModel)
}