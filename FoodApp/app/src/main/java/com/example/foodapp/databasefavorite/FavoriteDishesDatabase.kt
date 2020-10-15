package com.example.foodapp.databasefavorite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodapp.repositorydishbyid.DishByIdDataModel

@Database(entities = arrayOf(DishByIdDataModel::class), version = 1, exportSchema = false)
abstract class FavoriteDishesDatabase : RoomDatabase() {

    abstract fun getFavoriteDishesDao(): FavoriteDishesDao

    companion object {
        private var INSTANCE: FavoriteDishesDatabase? = null

        fun getDatabaseInstance(context: Context): FavoriteDishesDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteDishesDatabase::class.java,
                        "FAVORITES"
                )
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE as FavoriteDishesDatabase
        }
    }
}