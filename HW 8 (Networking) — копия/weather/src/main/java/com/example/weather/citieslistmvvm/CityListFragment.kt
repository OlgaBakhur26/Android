package com.example.weather.citieslistmvvm

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.weatherlistmvp.view.WeatherActivity
import com.example.weather.R
import kotlinx.android.synthetic.main.fragment_city.*
import kotlinx.android.synthetic.main.item_city.view.*
import java.util.*

class CityListFragment : Fragment() {

    private lateinit var cityDao: CityDao
    private lateinit var cityList: List<CityDataModel> // LiveDate

    private lateinit var cityNamePrefs: CityNamePrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appDatabase = this@CityListFragment.context?.let { AppDatabase.getAppDatabaseInstance(it) }
        if (appDatabase != null) {
            cityDao = appDatabase.getCityDao()
        }
        cityList = cityDao.getAll()

        Log.d("fun", "onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_city, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItemList()

        floatingButtonAddCity.setOnClickListener(View.OnClickListener {
            showDialog()
        })
    }

    private fun initItemList() {
        cityNamePrefs = CityNamePrefs.getCityNamePrefs(this@CityListFragment.context)
        viewCityList.apply {
                    adapter = CityAdapter(cityList, object : OnCityClickListener {
                        override fun onCityClick(cityDataModel: CityDataModel) {
                            cityNamePrefs.saveCityName(cityDataModel.cityName)
                            startWeatherActivity(cityDataModel)
                        }
                    })
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun startWeatherActivity(cityDataModel: CityDataModel){
        val intent = Intent(this@CityListFragment.context, WeatherActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        updateCityList()
    }

    private fun updateCityList() {
        val adapter = viewCityList.adapter as? CityAdapter
        adapter?.itemList = cityDao.getAll()
        adapter?.notifyDataSetChanged()
    }

    private fun showDialog(){
        val enterCity = EditText(this@CityListFragment.context)
        enterCity.hint = getString(R.string.dialogHint)

        val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )                                                            // это не работает

        enterCity.layoutParams = layoutParams

        val dialog = AlertDialog.Builder(this@CityListFragment.context, R.style.Theme_AppCompat_Light_Dialog)
        dialog
                .setTitle(R.string.dialogTitle)
                .setView(enterCity)
                .setPositiveButton(getString(R.string.dialogPositiveButton),
                        DialogInterface.OnClickListener { dialog, which ->
                            val newCity = CityDataModel(
                                    id = UUID.randomUUID().toString(),
                                    cityName = enterCity.text.toString()
                            )
                            cityDao.insert(newCity)
                        })
                        .setNegativeButton(getString(R.string.dialogNegativeButton),
                                DialogInterface.OnClickListener { dialog, which ->
                                })
                        .create()
        dialog.show()
    }



    // ADAPTER
    class CityAdapter(
            internal var itemList: List<CityDataModel>,
            private val cityListener: OnCityClickListener
    ) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder =
                CityViewHolder(
                        itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
                )

        override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
            holder.bind(itemList[position], cityListener)
        }

        override fun getItemCount(): Int = itemList.size


        class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private lateinit var cityNamePrefs: CityNamePrefs

            fun bind(cityDataModel: CityDataModel, cityListener: OnCityClickListener) {
                cityNamePrefs = CityNamePrefs.getCityNamePrefs()
                val cityName = cityNamePrefs.loadCityName()

                with(itemView) {
                    viewCityName.text = cityDataModel.cityName
                    tickMark.setImageResource(R.drawable.tick_mark)

                    if (cityName == cityDataModel.cityName){
                        tickMark.visibility = VISIBLE
                    }
                }
                itemView.setOnClickListener { cityListener.onCityClick(cityDataModel) }
            }
        }
    }

    companion object {
        const val TAG = "CityListFragment"

        @JvmStatic
        fun newInstance() = CityListFragment()
    }
}