package com.example.weather.weatherlistmvp.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.citieslistmvvm.view.CityActivity
import com.example.weather.settingscityname.CityNamePrefs
import com.example.weather.iconsmanager.IconsManager
import com.example.weather.settingsunit.UNIT_TYPE
import com.example.weather.settingsunit.UnitSettingsPrefs
import com.example.weather.settingsunit.UnitSettingsPrefs.getUnitSettings
import com.example.weather.weatherlistmvp.model.CurrentWeatherDataModelMapper
import com.example.weather.weatherlistmvp.model.WeatherListDataModelMapper
import com.example.weather.weatherlistmvp.model.WeatherRepositoryImpl
import com.example.weather.weatherlistmvp.presenter.CurrentWeatherDataModelUIMapper
import com.example.weather.weatherlistmvp.presenter.WeatherListDataModelUIMapper
import com.example.weather.weatherlistmvp.presenter.WeatherListPresenter
import com.example.weather.weatherlistmvp.presenter.WeatherListPresenterImpl
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.item_weather.view.*
import okhttp3.OkHttpClient

class WeatherListFragment : Fragment(), WeatherListView {

    private lateinit var unitSettingsPrefs: UnitSettingsPrefs
    private lateinit var unitType: UNIT_TYPE
    private lateinit var unit: String
    private lateinit var degreesLetterIdentifier: String

    private lateinit var cityNamePrefs: CityNamePrefs
    private lateinit var cityName: String

    private val presenter: WeatherListPresenter = WeatherListPresenterImpl(
            weatherListView = this,
            weatherRepository = WeatherRepositoryImpl(
                    okHttpClient = OkHttpClient(),
                    weatherListDataModelMapper = WeatherListDataModelMapper(),
                    currentWeatherDataModelMapper = CurrentWeatherDataModelMapper()
            ),
            weatherListDataModelUIMapper = WeatherListDataModelUIMapper(),
            currentWeatherDataModelUIMapper = CurrentWeatherDataModelUIMapper()
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_weather, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItemList()

        floatingButtonNavigateToCityActivity.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@WeatherListFragment.context, CityActivity::class.java)
            startActivity(intent)
        })
    }

    private fun initItemList() {
        viewWeatherList.apply {
            adapter = WeatherAdapter()
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onStart() {
        super.onStart()

        unitSettingsPrefs = getUnitSettings(this.context)
        unitType = UNIT_TYPE.valueOf(unitSettingsPrefs.loadUnitType())
        unit = unitType.measurement
        degreesLetterIdentifier = unitType.letterIdentifier

        cityNamePrefs = CityNamePrefs.getCityNamePrefs(this@WeatherListFragment.context)
        cityName = cityNamePrefs.loadCityName()
    }

    override fun onResume() {
        super.onResume()
        presenter.fetchWeatherList(cityName, unit)
        presenter.fetchCurrentWeather(cityName, unit)
    }

    override fun showWeatherList(weatherListDataModelUI: List<WeatherListDataModelUI>) {
        (viewWeatherList.adapter as? WeatherAdapter)?.loadItemList(weatherListDataModelUI)
    }

    override fun showCurrentWeather(currentWeatherDataModelUI: CurrentWeatherDataModelUI) {
        viewCity.text = currentWeatherDataModelUI.city
        viewTemperature.text = "${currentWeatherDataModelUI.temperature} $degreesLetterIdentifier"
        viewDescription.text = "now ${currentWeatherDataModelUI.description}"

        val iconManager = IconsManager.instance
        iconManager.loadIcon(currentWeatherDataModelUI, viewIcon)
    }

    override fun onErrorWeatherList(errorMessage: String) =
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()


    override fun onErrorCurrentWeather(errorMessage: String) =
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }


    // ADAPTER
    class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

        private val itemList = mutableListOf<WeatherListDataModelUI>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder =
                WeatherViewHolder(
                        itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
                )

        override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
            holder.bind(itemList[position])
        }

        override fun getItemCount() = itemList.size

        fun loadItemList(newItemList: List<WeatherListDataModelUI>) {
            itemList.apply {
                clear()
                addAll(newItemList)
            }
            notifyDataSetChanged()
        }


        // ViewHolder
        class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private lateinit var unitSettingsPrefs: UnitSettingsPrefs
            private lateinit var unitType: UNIT_TYPE
            private lateinit var degreesLetterIdentifier: String

            fun bind(weatherListDataModelUI: WeatherListDataModelUI) {
                degreesLetterIdentifier = identifyDegrees()

                with(itemView) {
                    viewTime.text = weatherListDataModelUI.time
                    viewDescription.text = weatherListDataModelUI.description
                    viewTemperature.text = "${weatherListDataModelUI.temperature} $degreesLetterIdentifier"

                    val iconManager = IconsManager.instance
                    iconManager.loadIcon(weatherListDataModelUI, viewIcon)
                }
            }

            private fun identifyDegrees(): String {
                unitSettingsPrefs = getUnitSettings()
                unitType = UNIT_TYPE.valueOf(unitSettingsPrefs.loadUnitType())
                return unitType.letterIdentifier
            }
        }
    }

    companion object {
        const val TAG = "WeatherListFragment"

        @JvmStatic
        fun newInstance() = WeatherListFragment()
    }
}