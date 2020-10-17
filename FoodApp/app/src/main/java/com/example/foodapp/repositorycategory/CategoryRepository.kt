package com.example.foodapp.repositorycategory

import io.reactivex.Single

interface CategoryRepository {
    fun getCategoryList(): Single<List<CategoryListDataModel>>
}