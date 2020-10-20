package com.example.weather.citieslistmvvm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CITY_TABLE")
data class CityDataModel(
        @PrimaryKey()
        @ColumnInfo(index = true)
        val id: String,
        @ColumnInfo(index = true)
        val cityName: String
)