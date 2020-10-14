package com.example.foodapp.repositorydishbyid

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder
import java.util.regex.Pattern

class DishByIdDataModelMapper : (String) -> DishByIdDataModel{
    override fun invoke(jsonData: String): DishByIdDataModel {
        val jsonObject = JSONObject(jsonData)
        val jsonListArray = jsonObject.getJSONArray("meals")

        val ingredientsTitles = getIngredientsTitles(jsonListArray)
        val ingredientsList = getIngredientsList(jsonListArray, ingredientsTitles)

        val measureTitles = getMeasureTitles(jsonListArray)
        val measureList = getMeasureList(jsonListArray, measureTitles)

        val ingredients = concatIngredientsAndMeasures(ingredientsList, measureList)

        Log.d("TAG", "ingredientsTitles: ${ingredientsTitles.toString()}")
        Log.d("TAG", "ingredientsList: ${ingredientsList.toString()}")
        Log.d("TAG", "measureTitles: ${measureTitles.toString()}")
        Log.d("TAG", "measureList: ${measureList.toString()}")

        return DishByIdDataModel(
            dishId = jsonListArray.getJSONObject(0).getString("idMeal"),
            dishName = jsonListArray.getJSONObject(0).getString("strMeal"),
            urlToImage = jsonListArray.getJSONObject(0).getString("strMealThumb"),
            urlToVideo = jsonListArray.getJSONObject(0).getString("strYoutube"),
            categoryName = jsonListArray.getJSONObject(0).getString("strCategory"),
            areaName = jsonListArray.getJSONObject(0).getString("strArea"),
            ingredients = ingredients,
            instructions = jsonListArray.getJSONObject(0).getString("strInstructions")
        )
    }

    private fun getIngredientsTitles(jsonListArray: JSONArray): MutableList<String>{
        val pattern = Pattern.compile("strIngredient\\d+")
        val matcher = pattern.matcher(jsonListArray.getJSONObject(0).toString())
        val listOfTitles = mutableListOf<String>()
        while (matcher.find()){
            listOfTitles.add(matcher.group())
        }
        return listOfTitles
    }

    private fun getIngredientsList(jsonListArray: JSONArray, ingredientsTitles: MutableList<String>): MutableList<String>{
        val listOfIngredients = mutableListOf<String>()
        for (index in 0 until ingredientsTitles.size){
            val ingredient = jsonListArray.getJSONObject(0).getString(ingredientsTitles[index])
            if (ingredient != "" && ingredient != "null"){
                listOfIngredients.add(ingredient)
            }
        }
        return listOfIngredients
    }

    private fun getMeasureTitles(jsonListArray: JSONArray): MutableList<String>{
        val pattern = Pattern.compile("strMeasure\\d+")
        val matcher = pattern.matcher(jsonListArray.getJSONObject(0).toString())
        val listOfTitles = mutableListOf<String>()
        while (matcher.find()){
            listOfTitles.add(matcher.group())
        }
        return listOfTitles
    }

    private fun getMeasureList(jsonListArray: JSONArray, measureTitles: MutableList<String>): MutableList<String>{
        val listOfMeasures = mutableListOf<String>()
        for (index in 0 until measureTitles.size){
            val measure = jsonListArray.getJSONObject(0).getString(measureTitles[index])
            if (measure != "" && measure != "null"){
                listOfMeasures.add(measure)
            }
        }
        return listOfMeasures
    }

    private fun concatIngredientsAndMeasures(ingredientsList: MutableList<String>, measureList: MutableList<String>): String{
        val stringBuilder = StringBuilder()
        for (index in 0 until ingredientsList.size){
            for (index in 0 until measureList.size){
                stringBuilder.append(ingredientsList[index])
                stringBuilder.append(": ")
                stringBuilder.append(measureList[index])
                stringBuilder.append("\n")
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length - 1)
        return stringBuilder.toString()
    }
}

