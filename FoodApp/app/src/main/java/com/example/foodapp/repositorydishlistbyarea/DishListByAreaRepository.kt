package com.example.foodapp.repositorydishlistbyarea

import io.reactivex.Single

interface DishListByAreaRepository {
    fun getDishListByArea(areaName: String): Single<List<DishListByAreaDataModel>>
}