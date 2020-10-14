package com.example.foodapp.repositorydishlistbycategory

import io.reactivex.Single

interface DishListByCategoryRepository{
    fun getDishListByCategory(categoryName: String): Single<List<DishListByCategoryDataModel>>
}