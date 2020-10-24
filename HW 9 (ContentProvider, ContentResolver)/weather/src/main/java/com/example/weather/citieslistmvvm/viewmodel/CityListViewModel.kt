package com.example.weather.citieslistmvvm.viewmodel

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.citieslistmvvm.model.AppDatabase
import com.example.weather.citieslistmvvm.model.CityDao
import com.example.weather.citieslistmvvm.model.CityDataModel

class CityListViewModel : ViewModel() {

    private val mutableLiveData = MutableLiveData<List<CityDataModel>>()
    lateinit var liveData: LiveData<List<CityDataModel>>

    private lateinit var cityDao: CityDao

    fun loadCityList(context: Context): LiveData<List<CityDataModel>>{
        val appDatabase = AppDatabase.getAppDatabaseInstance(context)
        if (appDatabase != null) {
            cityDao = appDatabase.getCityDao()
        }
        mutableLiveData.postValue(cityDao.getAll())
        liveData = mutableLiveData
        return liveData
    }

    fun addCity(context: Context, cityDataModel: CityDataModel){
        val appDatabase = AppDatabase.getAppDatabaseInstance(context)
        if (appDatabase != null) {
            cityDao = appDatabase.getCityDao()
        }
        cityDao.insert(cityDataModel)
    }
}