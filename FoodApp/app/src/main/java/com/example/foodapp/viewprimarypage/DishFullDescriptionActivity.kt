package com.example.foodapp.viewprimarypage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.repositorycategory.CategoryListDataModelMapper
import com.example.foodapp.repositorycategory.CategoryRepositoryImpl
import com.example.foodapp.repositorydishbyid.DishByIdDataModelMapper
import com.example.foodapp.repositorydishbyid.DishByIdRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_dish_full_description.*
import kotlinx.android.synthetic.main.activity_dish_full_description.viewDishName
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_random_dish_display_dish.*
import okhttp3.OkHttpClient

const val KEY_EXTRA_DISH_ID = "KEY_EXTRA_DISH_ID"

class DishFullDescriptionActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    private lateinit var dishId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_full_description)

        setSupportActionBar(toolbarDishFullDescriptionActivity)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        dishId = getDishId()
        fetchDishById()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getDishId(): String{
        val intent = intent
        return intent.getStringExtra(KEY_EXTRA_DISH_ID) as String
    }

    private fun fetchDishById() {
        disposable = DishByIdRepositoryImpl(
            okHttpClient = OkHttpClient(),
            dishByIdDataModelMapper = DishByIdDataModelMapper()
        ).getDishById(dishId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { dish ->
                    with(dish){
                        viewDishName.text = dishName
                        viewDishCategory.text = categoryName
                        viewDishArea.text = areaName
                        viewDishIngredientsList.text = ingredients
                        viewInstruction.text = instructions

                        Glide.with(this@DishFullDescriptionActivity)
                            .load(urlToImage)
                            .into(viewDishPhoto)

                        /// ссылка на youTube
                        //// recyclerViewNotes
                    }
                },
                { throwable -> Log.d("DishFullDescriptionActivity", throwable.toString()) }
            )

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    companion object {
        fun newInstance() = DishFullDescriptionActivity()
    }

    fun newIntent(context: Context): Intent {
        return Intent(context, DishFullDescriptionActivity::class.java)
    }
}