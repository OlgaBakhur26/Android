package com.example.weather.weatherlistmvp.model

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody

private const val API_KEY = "1ddee5e3520c06bbf346e6cb9046b969"

class WeatherRepositoryImpl(
        private val okHttpClient: OkHttpClient,
        private val weatherListDataModelMapper: (String) -> List<WeatherListDataModel>,
        private val currentWeatherDataModelMapper: (String) -> CurrentWeatherDataModel
) : WeatherRepository {

    override fun getWeatherList(city: String, unit: String): Single<List<WeatherListDataModel>> {
        val url = "https://api.openweathermap.org/data/2.5/forecast?q=$city&units=$unit&lang=en&mode=json&appid=$API_KEY"
        val request = Request.Builder()
                .url(url)
                .build()

        return Single.create<String> { emitter ->
            okHttpClient.newCall(request).execute()
                    .use { response ->
                        if (!response.isSuccessful) {
                            emitter.onError(Throwable(response.code.toString()))
                        }
                        if (response.body == null) {
                            emitter.onError(NullPointerException("Body is null"))
                        }
                        emitter.onSuccess((response.body as ResponseBody).string())
                    }
        }
                .subscribeOn(Schedulers.io())
                .map { jsonData -> weatherListDataModelMapper(jsonData) }
    }

    override fun getCurrentWeather(city: String, unit: String): Single<CurrentWeatherDataModel> {
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&units=$unit&lang=en&mode=json&appid=$API_KEY"
        val request = Request.Builder()
                .url(url)
                .build()

        return Single.create<String> { emitter ->
            okHttpClient.newCall(request).execute()
                    .use { response ->
                        if (!response.isSuccessful) {
                            emitter.onError(Throwable(response.code.toString()))
                        }
                        if (response.body == null) {
                            emitter.onError(NullPointerException("Body is null"))
                        }
                        emitter.onSuccess((response.body as ResponseBody).string())
                    }
        }
                .subscribeOn(Schedulers.io())
                .map { jsonData -> currentWeatherDataModelMapper(jsonData) }
    }
}