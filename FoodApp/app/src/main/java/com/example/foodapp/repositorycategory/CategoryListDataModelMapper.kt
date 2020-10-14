package com.example.foodapp.repositorycategory

import android.util.Log
import org.json.JSONObject

class CategoryListDataModelMapper : (String) -> List<CategoryListDataModel>{
    override fun invoke(jsonData: String): List<CategoryListDataModel> {
        val jsonObject = JSONObject(jsonData)
        val jsonListArray = jsonObject.getJSONArray("meals")
        val itemList = mutableListOf<CategoryListDataModel>()

        if (jsonListArray.length() > 0) {
            for (index in 0 until jsonListArray.length()) {
                val jsonListItem = jsonListArray.getJSONObject(index)
                val categoryListDataModel = CategoryListDataModel(
                    dishCategory = jsonListItem.getString("strCategory")
                )
                itemList.add(categoryListDataModel)
            }
            return itemList
        }
        return emptyList()
    }
}

