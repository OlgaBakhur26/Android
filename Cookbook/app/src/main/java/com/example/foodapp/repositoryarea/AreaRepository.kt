package com.example.foodapp.repositoryarea

import io.reactivex.Single

interface AreaRepository {
    fun getAreaList(): Single<List<AreaListDataModel>>
}