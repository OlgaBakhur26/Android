package com.example.foodapp.imagemanager

import android.widget.ImageView
import com.example.foodapp.R
import com.example.foodapp.repositorycategory.CategoryListDataModel

class CategoryImageManager {
    companion object {
        private var categoryImageManager: CategoryImageManager? = null

        val instance: CategoryImageManager
            get() {
                if (categoryImageManager == null) categoryImageManager = CategoryImageManager()
                return categoryImageManager!!
            }
    }

    fun loadImage(categoryListDataModel: CategoryListDataModel, imageView: ImageView) {
        when (categoryListDataModel.dishCategory) {
            "Beef" -> imageView.setImageResource(R.drawable.beef)
            "Breakfast" -> imageView.setImageResource(R.drawable.breakfast)
            "Chicken" -> imageView.setImageResource(R.drawable.chicken)
            "Dessert" -> imageView.setImageResource(R.drawable.dessert)
            "Goat" -> imageView.setImageResource(R.drawable.goat)
            "Lamb" -> imageView.setImageResource(R.drawable.lamb)
            "Miscellaneous" -> imageView.setImageResource(R.drawable.miscellaneous)
            "Pasta" -> imageView.setImageResource(R.drawable.pasta)
            "Pork" -> imageView.setImageResource(R.drawable.pork)
            "Seafood" -> imageView.setImageResource(R.drawable.seafood)
            "Side" -> imageView.setImageResource(R.drawable.side)
            "Vegan" -> imageView.setImageResource(R.drawable.vegan)
            "Vegetarian" -> imageView.setImageResource(R.drawable.vegetarian)
        }
    }
}