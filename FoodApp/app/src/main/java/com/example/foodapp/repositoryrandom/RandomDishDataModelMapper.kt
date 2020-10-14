package com.example.foodapp.repositoryrandom

import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder
import java.util.regex.Pattern

class RandomDishDataModelMapper : (String) -> RandomDishDataModel{
    override fun invoke(jsonData: String): RandomDishDataModel {
        val jsonObject = JSONObject(jsonData)
        val jsonListArray = jsonObject.getJSONArray("meals")

        val ingredientsTitles = getIngredientsTitles(jsonListArray)
        val dishIngredients = getIngredientsList(jsonListArray, ingredientsTitles)

        return RandomDishDataModel(
            dishId = jsonListArray.getJSONObject(0).getString("idMeal"),
            dishName = jsonListArray.getJSONObject(0).getString("strMeal"),
            dishCategory = jsonListArray.getJSONObject(0).getString("strCategory"),
            dishArea = jsonListArray.getJSONObject(0).getString("strArea"),
            dishIngredients = dishIngredients,
            urlToImage = jsonListArray.getJSONObject(0).getString("strMealThumb")
        )
    }

    // Тут еще можно добавить проверку на непустой jsonListArray - иначе Optional

    private fun getIngredientsTitles(jsonListArray: JSONArray): MutableList<String>{
        val pattern = Pattern.compile("strIngredient\\d+")
        val matcher = pattern.matcher(jsonListArray.getJSONObject(0).toString())
        val list = mutableListOf<String>()
        while (matcher.find()){
            list.add(matcher.group())
        }
        return list
    }

    private fun getIngredientsList(jsonListArray: JSONArray, ingredientsTitles: MutableList<String>): String{
        val stringBuilder = StringBuilder()
        for (index in 0 until ingredientsTitles.size){
            val ingredient = jsonListArray.getJSONObject(0).getString(ingredientsTitles[index])
            if (ingredient != "" && ingredient != "null"){
                stringBuilder.append(ingredient)
                stringBuilder.append(", ")
            }
        }

        stringBuilder.delete(stringBuilder.length - 2, stringBuilder.length)
        stringBuilder.append(".")
        return stringBuilder.toString()
    }
}

