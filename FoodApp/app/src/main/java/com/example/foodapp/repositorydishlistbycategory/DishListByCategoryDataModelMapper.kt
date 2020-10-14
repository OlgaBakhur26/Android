package com.example.foodapp.repositorydishlistbycategory

import org.json.JSONObject

class DishListByCategoryDataModelMapper : (String) -> List<DishListByCategoryDataModel>{
    override fun invoke(jsonData: String): List<DishListByCategoryDataModel> {
        val jsonObject = JSONObject(jsonData)
        val jsonListArray = jsonObject.getJSONArray("meals")
        val itemList = mutableListOf<DishListByCategoryDataModel>()

        if (jsonListArray.length() > 0) {
            for (index in 0 until jsonListArray.length()) {
                val jsonListItem = jsonListArray.getJSONObject(index)
                val dishListByCategoryDataModel = DishListByCategoryDataModel(
                    dishId = jsonListItem.getString("idMeal"),
                    dishName = jsonListItem.getString("strMeal"),
                    urlToImage = jsonListItem.getString("strMealThumb")
                )
                itemList.add(dishListByCategoryDataModel)
            }
            return itemList
        }
        return emptyList()
    }
}

