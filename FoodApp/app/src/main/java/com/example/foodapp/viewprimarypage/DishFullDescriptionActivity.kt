package com.example.foodapp.viewprimarypage

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databasefavorite.FavoriteDishesDao
import com.example.foodapp.databasefavorite.FavoriteDishesDatabase
import com.example.foodapp.repositorydishbyid.DishByIdDataModel
import com.example.foodapp.repositorydishbyid.DishByIdDataModelMapper
import com.example.foodapp.repositorydishbyid.DishByIdRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_dish_full_description.*
import kotlinx.android.synthetic.main.activity_dish_full_description.viewDishName
import okhttp3.OkHttpClient

const val KEY_EXTRA_DISH_ID = "KEY_EXTRA_DISH_ID"

class DishFullDescriptionActivity : AppCompatActivity(){

    private var disposable: Disposable? = null
    private lateinit var dishId: String
    private lateinit var favoriteDishesDao: FavoriteDishesDao
    private lateinit var dishByIdDataModel: DishByIdDataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_full_description)

        setSupportActionBar(toolbarDishFullDescriptionActivity)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val favoriteDishesDatabase = FavoriteDishesDatabase.getDatabaseInstance(this)
        if(favoriteDishesDatabase != null){
            favoriteDishesDao = favoriteDishesDatabase.getFavoriteDishesDao()
        }

        dishId = getDishId()
        fetchDishById()

        whetherIsFavorite(dishId)

        viewAddToFavorite.setOnClickListener {
            if(favoriteDishesDao.getById(dishId) == null){
                favoriteDishesDao.insert(dishByIdDataModel)
            }else{
                favoriteDishesDao.delete(dishByIdDataModel)
            }
        }
    }

    private fun whetherIsFavorite(dishId: String){
        if(favoriteDishesDao.getById(dishId) != null){
            viewAddToFavorite.setColorFilter(Color.RED)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigateToFavoriteDishesActivity -> startFavoriteDishesActivity()
            android.R.id.home ->  finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startFavoriteDishesActivity(){
        val instance = FavoriteDishesActivity.newInstance()
        val intent = instance.newIntent(this)
        startActivity(intent)
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

                        viewIconYouTube.setOnClickListener {
                            YouTubeActivity.newInstance().newIntent(this@DishFullDescriptionActivity)
                            startYouTubeActivity(this@DishFullDescriptionActivity, urlToVideo)
                        }

                        Glide.with(this@DishFullDescriptionActivity)
                            .load(urlToImage)
                            .into(viewDishPhoto)

                        //// recyclerViewNotes
                    }
                    dishByIdDataModel = dish
                },
                { throwable -> Log.d("DishFullDescriptionActivity", throwable.toString()) }
            )
    }

    private fun startYouTubeActivity(context: Context, urlToVideo: String){
        val instance = YouTubeActivity.newInstance()
        val intent = instance.newIntent(context)
        intent.putExtra(KEY_EXTRA_URL_TO_VIDEO, urlToVideo)
        startActivity(intent)
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