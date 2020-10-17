package com.example.foodapp.databasefavorite

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodapp.repositorydishbyid.DishByIdDataModel

@Dao
interface FavoriteDishesDao {

    @Query("SELECT * FROM FAVORITES")
    fun getAll(): LiveData<List<DishByIdDataModel>>

    @Query("SELECT * FROM FAVORITES WHERE dishId LIKE :dishId")
    fun getByIdLiveData(dishId: String): LiveData<DishByIdDataModel>

    @Query("SELECT * FROM FAVORITES WHERE dishId LIKE :dishId")
    fun getByIdGeneral(dishId: String): DishByIdDataModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dishByIdDataModel: DishByIdDataModel)

    @Update
    fun update(dishByIdDataModel: DishByIdDataModel)

    @Delete
    fun delete(dishByIdDataModel: DishByIdDataModel)
}