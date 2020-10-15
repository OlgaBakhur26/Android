package com.example.foodapp.databasefavorite

import androidx.room.*
import com.example.foodapp.repositorydishbyid.DishByIdDataModel

@Dao
interface FavoriteDishesDao {

    @Query("SELECT * FROM FAVORITES")
    fun getAll(): List<DishByIdDataModel>

    @Query("SELECT * FROM FAVORITES WHERE dishId LIKE :dishId")
    fun getById(dishId: String): DishByIdDataModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dishByIdDataModel: DishByIdDataModel)

    @Update
    fun update(dishByIdDataModel: DishByIdDataModel)

    @Delete
    fun delete(dishByIdDataModel: DishByIdDataModel)
}