package com.example.foodapp.repositorydishbyid

import io.reactivex.Single

interface DishByIdRepository {
    fun getDishById(id: String): Single<DishByIdDataModel>
}