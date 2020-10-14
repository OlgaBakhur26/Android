package com.example.foodapp.repositoryarea

import android.util.Log
import org.json.JSONObject

class AreaListDataModelMapper : (String) -> List<AreaListDataModel> {
    override fun invoke(jsonData: String): List<AreaListDataModel> {
        val jsonObject = JSONObject(jsonData)
        val jsonListArray = jsonObject.getJSONArray("meals")
        val itemList = mutableListOf<AreaListDataModel>()

        if (jsonListArray.length() > 0) {
            for (index in 0 until jsonListArray.length()) {
                val jsonListItem = jsonListArray.getJSONObject(index)
                val categoryListDataModel = AreaListDataModel(
                    dishArea = jsonListItem.getString("strArea")
                )
                itemList.add(categoryListDataModel)
            }
            return itemList
        }
        return emptyList()
    }
}

