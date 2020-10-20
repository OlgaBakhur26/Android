package com.example.weather.weatherlistmvp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.R
import com.example.weather.citieslistmvvm.CityNamePrefs
import com.example.weather.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_weather.*


class WeatherActivity : AppCompatActivity() {

    private var cityNamePrefs: CityNamePrefs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        setSupportActionBar(toolbarMainActivity)
//        cityNamePrefs = CityNamePrefs.getCityNamePrefs(this)
        showWeatherListFragment()

        settingsButton.setOnClickListener(View.OnClickListener {
            startActivity(SettingsActivity.newIntent(this))
        })
    }

    private fun showWeatherListFragment() {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, WeatherListFragment.newInstance(), WeatherListFragment.TAG)
                .commit()
    }
}