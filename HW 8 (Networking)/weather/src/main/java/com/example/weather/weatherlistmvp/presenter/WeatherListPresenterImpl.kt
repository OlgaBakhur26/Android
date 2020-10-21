package com.example.weather.weatherlistmvp.presenter

import com.example.weather.weatherlistmvp.model.CurrentWeatherDataModel
import com.example.weather.weatherlistmvp.model.WeatherListDataModel
import com.example.weather.weatherlistmvp.model.WeatherRepository
import com.example.weather.weatherlistmvp.view.CurrentWeatherDataModelUI
import com.example.weather.weatherlistmvp.view.WeatherListDataModelUI
import com.example.weather.weatherlistmvp.view.WeatherListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class WeatherListPresenterImpl(
        private var weatherListView: WeatherListView?,
        private val weatherRepository: WeatherRepository,
        private val weatherListDataModelUIMapper: (List<WeatherListDataModel>) -> List<WeatherListDataModelUI>,
        private val currentWeatherDataModelUIMapper: (CurrentWeatherDataModel) -> CurrentWeatherDataModelUI
) : WeatherListPresenter {

    private var disposable: Disposable? = null

    override fun fetchWeatherList(cityName: String, unit: String) {
        disposable = weatherRepository.getWeatherList(cityName, unit)
                .map { weatherListDataModelUIMapper(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { list -> weatherListView?.showWeatherList(list) },
                        { throwable -> weatherListView?.onErrorWeatherList("WeatherList Error occurred: $throwable") }
                )
    }

    override fun fetchCurrentWeather(cityName: String, unit: String) {
        disposable = weatherRepository.getCurrentWeather(cityName, unit)
                .map { currentWeatherDataModelUIMapper(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currentWeather -> weatherListView?.showCurrentWeather(currentWeather) },
                        { throwable -> weatherListView?.onErrorCurrentWeather("CurrentWeather Error occurred: $throwable") }
                )
    }

    override fun dispose() {
        disposable?.dispose()
        weatherListView = null
    }
}