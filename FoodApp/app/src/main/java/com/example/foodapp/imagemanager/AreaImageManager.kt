package com.example.foodapp.imagemanager

import android.widget.ImageView
import com.example.foodapp.R
import com.example.foodapp.repositoryarea.AreaListDataModel
import com.example.foodapp.repositorycategory.CategoryListDataModel

class AreaImageManager {
    companion object {
        private var areaImageManager: AreaImageManager? = null

        val instance: AreaImageManager
            get() {
                if (areaImageManager == null) areaImageManager = AreaImageManager()
                return areaImageManager!!
            }
    }

    fun loadImage(areaListDataModel: AreaListDataModel, imageView: ImageView) {
        when (areaListDataModel.dishArea) {
            "American" -> imageView.setImageResource(R.drawable.american)
            "British" -> imageView.setImageResource(R.drawable.british)
            "Canadian" -> imageView.setImageResource(R.drawable.canadian)
            "Chinese" -> imageView.setImageResource(R.drawable.chinese)
            "Dutch" -> imageView.setImageResource(R.drawable.dutch)
            "Egyptian" -> imageView.setImageResource(R.drawable.egyptian)
            "French" -> imageView.setImageResource(R.drawable.french)
            "Greek" -> imageView.setImageResource(R.drawable.greek)
            "Indian" -> imageView.setImageResource(R.drawable.indian)
            "Irish" -> imageView.setImageResource(R.drawable.irish)
            "Italian" -> imageView.setImageResource(R.drawable.italian)
            "Jamaican" -> imageView.setImageResource(R.drawable.jamaican)
            "Japanese" -> imageView.setImageResource(R.drawable.japanese)
            "Kenyan" -> imageView.setImageResource(R.drawable.kenyan)
            "Malaysian" -> imageView.setImageResource(R.drawable.malaysian)
            "Mexican" -> imageView.setImageResource(R.drawable.mexican)
            "Moroccan" -> imageView.setImageResource(R.drawable.moroccan)
            "Polish" -> imageView.setImageResource(R.drawable.polish)
            "Russian" -> imageView.setImageResource(R.drawable.russian)
            "Spanish" -> imageView.setImageResource(R.drawable.spanish)
            "Tunisian" -> imageView.setImageResource(R.drawable.tunisian)
            "Turkish" -> imageView.setImageResource(R.drawable.turkish)
            "Unknown" -> imageView.setImageResource(R.drawable.unknown)
            "Vietnamese" -> imageView.setImageResource(R.drawable.vietnamese)
        }
    }
}