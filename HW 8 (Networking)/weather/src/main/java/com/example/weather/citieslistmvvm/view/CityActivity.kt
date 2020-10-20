package com.example.weather.citieslistmvvm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.R
import kotlinx.android.synthetic.main.activity_city.*

class CityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        setSupportActionBar(toolbarCityActivity)
        showCityListFragment()
    }

    private fun showCityListFragment() {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, CityListFragment.newInstance(), CityListFragment.TAG)
                .commit()
    }
}