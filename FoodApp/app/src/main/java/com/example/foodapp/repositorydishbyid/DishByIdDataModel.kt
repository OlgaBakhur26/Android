package com.example.foodapp.repositorydishbyid

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FAVORITES")
data class DishByIdDataModel(
    @PrimaryKey
    val dishId: String,
    val dishName: String,
    val urlToImage: String,
    val urlToVideo: String,
    val categoryName: String,
    val areaName: String,
    val ingredients: String,
    val instructions: String,
)