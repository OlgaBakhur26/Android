package com.example.foodapp.viewprimarypage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.foodapp.R
import kotlinx.android.synthetic.main.activity_dish_full_description.*
import kotlinx.android.synthetic.main.activity_dish_full_description.toolbarDishFullDescriptionActivity
import kotlinx.android.synthetic.main.activity_favorite_dishes.*

class FavoriteDishesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_dishes)

        setSupportActionBar(toolbarFavoriteDishes)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newInstance() = FavoriteDishesActivity()
    }

    fun newIntent(context: Context): Intent {
        return Intent(context, FavoriteDishesActivity::class.java)
    }
}