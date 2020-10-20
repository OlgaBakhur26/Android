package com.example.weather.citieslistmvvm

import androidx.room.*

@Dao
interface CityDao {

    @Query("SELECT * FROM CITY_TABLE")
    fun getAll(): List<CityDataModel>

    @Query("SELECT * FROM CITY_TABLE WHERE cityName LIKE :cityName")
    fun getByName(cityName: String): CityDataModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityDataModel: CityDataModel)

    @Update
    fun update(cityDataModel: CityDataModel)

    @Delete
    fun delete(cityDataModel: CityDataModel)
}