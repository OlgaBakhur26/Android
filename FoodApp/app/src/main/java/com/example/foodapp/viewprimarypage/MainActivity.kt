package com.example.foodapp.viewprimarypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.foodapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnItemCategoryClickListener, OnItemAreaClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbarPrimaryPage)

        showFragmentCategory()
        showFragmentArea()
        showFragmentRandomDishPrimary()
        floatingButton.setOnClickListener { showFragmentRandomDishDisplayDish() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigateToFavoriteDishesActivity -> startFavoriteDishesActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startFavoriteDishesActivity(){
        val instance = FavoriteDishesActivity.newInstance()
        val intent = instance.newIntent(this)
        startActivity(intent)
    }

    private fun showFragmentCategory() {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragmentContainerCategory,
                FragmentCategory.newInstance(),
                FragmentCategory.TAG
            )
            .commit()
    }

    private fun showFragmentArea() {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragmentContainerArea,
                FragmentArea.newInstance(),
                FragmentArea.TAG
            )
            .commit()
    }

    private fun showFragmentRandomDishPrimary() {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragmentContainerRandomDish,
                FragmentRandomDishPrimary.newInstance(),
                FragmentRandomDishPrimary.TAG
            )
            .commit()
    }

    private fun showFragmentRandomDishDisplayDish() {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragmentContainerRandomDish,
                FragmentRandomDishDisplayDish.newInstance(),
                FragmentRandomDishDisplayDish.TAG
            )
            .commit()
    }

    override fun displayItemCategory(categoryName: String) {
        val categoryListActivity = CategoryListActivity.newInstance()
        val intent = categoryListActivity.newIntent(this)
        intent.putExtra(KEY_EXTRA_CATEGORY_NAME, categoryName)
        startActivity(intent)
    }

    override fun displayItemArea(areaName: String) {
        val areaListActivity = AreaListActivity.newInstance()
        val intent = areaListActivity.newIntent(this)
        intent.putExtra(KEY_EXTRA_AREA_NAME, areaName)
        startActivity(intent)
    }
}