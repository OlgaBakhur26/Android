package com.example.weather.iconsmanager

import android.widget.ImageView
import com.example.weather.R
import com.example.weather.weatherlistmvp.view.CurrentWeatherDataModelUI
import com.example.weather.weatherlistmvp.view.WeatherListDataModelUI

class IconsManager {
    companion object {
        private var iconsManager: IconsManager? = null

        val instance: IconsManager
            get() {
                if (iconsManager == null) iconsManager = IconsManager()
                return iconsManager!!
            }
    }

    fun loadIcon(currentWeatherDataModelUI: CurrentWeatherDataModelUI, viewIcon: ImageView) {
        when (currentWeatherDataModelUI.icon) {
            "01d" -> viewIcon.setImageResource(R.drawable.ic_01d)
            "01n" -> viewIcon.setImageResource(R.drawable.ic_01n)
            "02d" -> viewIcon.setImageResource(R.drawable.ic_02d)
            "02n" -> viewIcon.setImageResource(R.drawable.ic_02n)
            "03d" -> viewIcon.setImageResource(R.drawable.ic_03d)
            "03n" -> viewIcon.setImageResource(R.drawable.ic_03n)
            "04d" -> viewIcon.setImageResource(R.drawable.ic_04d)
            "04n" -> viewIcon.setImageResource(R.drawable.ic_04n)
            "09d" -> viewIcon.setImageResource(R.drawable.ic_09d)
            "09n" -> viewIcon.setImageResource(R.drawable.ic_09n)
            "10d" -> viewIcon.setImageResource(R.drawable.ic_10d)
            "10n" -> viewIcon.setImageResource(R.drawable.ic_10n)
            "11d" -> viewIcon.setImageResource(R.drawable.ic_11d)
            "11n" -> viewIcon.setImageResource(R.drawable.ic_11n)
            "13d" -> viewIcon.setImageResource(R.drawable.ic_13d)
            "13n" -> viewIcon.setImageResource(R.drawable.ic_13n)
            "50d" -> viewIcon.setImageResource(R.drawable.ic_50d)
            "50n" -> viewIcon.setImageResource(R.drawable.ic_50n)
        }
    }

    fun loadIcon(weatherListDataModelUI: WeatherListDataModelUI, viewIcon: ImageView) {
        when (weatherListDataModelUI.icon) {
            "01d" -> viewIcon.setImageResource(R.drawable.ic_01d)
            "01n" -> viewIcon.setImageResource(R.drawable.ic_01n)
            "02d" -> viewIcon.setImageResource(R.drawable.ic_02d)
            "02n" -> viewIcon.setImageResource(R.drawable.ic_02n)
            "03d" -> viewIcon.setImageResource(R.drawable.ic_03d)
            "03n" -> viewIcon.setImageResource(R.drawable.ic_03n)
            "04d" -> viewIcon.setImageResource(R.drawable.ic_04d)
            "04n" -> viewIcon.setImageResource(R.drawable.ic_04n)
            "09d" -> viewIcon.setImageResource(R.drawable.ic_09d)
            "09n" -> viewIcon.setImageResource(R.drawable.ic_09n)
            "10d" -> viewIcon.setImageResource(R.drawable.ic_10d)
            "10n" -> viewIcon.setImageResource(R.drawable.ic_10n)
            "11d" -> viewIcon.setImageResource(R.drawable.ic_11d)
            "11n" -> viewIcon.setImageResource(R.drawable.ic_11n)
            "13d" -> viewIcon.setImageResource(R.drawable.ic_13d)
            "13n" -> viewIcon.setImageResource(R.drawable.ic_13n)
            "50d" -> viewIcon.setImageResource(R.drawable.ic_50d)
            "50n" -> viewIcon.setImageResource(R.drawable.ic_50n)
        }
    }
}