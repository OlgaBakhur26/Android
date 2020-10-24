package com.example.weather.citieslistmvvm.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CityDataModel::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCityDao(): CityDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getAppDatabaseInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "CITY_TABLE"
                )
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE as AppDatabase
        }
    }
}