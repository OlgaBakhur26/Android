package com.example.foodapp.repositorydishlistbyarea

import org.json.JSONObject

class DishListByAreaDataModelMapper : (String) -> List<DishListByAreaDataModel> {
    override fun invoke(jsonData: String): List<DishListByAreaDataModel> {
        val jsonObject = JSONObject(jsonData)
        val jsonListArray = jsonObject.getJSONArray("meals")
        val itemList = mutableListOf<DishListByAreaDataModel>()

        if (jsonListArray.length() > 0) {
            for (index in 0 until jsonListArray.length()) {
                val jsonListItem = jsonListArray.getJSONObject(index)
                val dishListByAreaDataModel = DishListByAreaDataModel(
                    dishId = jsonListItem.getString("idMeal"),
                    dishName = jsonListItem.getString("strMeal"),
                    urlToImage = jsonListItem.getString("strMealThumb")
                )
                itemList.add(dishListByAreaDataModel)
            }
            return itemList
        }
        return emptyList()
    }
}

