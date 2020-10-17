package com.example.foodapp.repositoryrandom

import io.reactivex.Single

interface RandomDishRepository {
    fun getRandomDish(): Single<RandomDishDataModel>
}