package com.example.foodapp.viewprimarypage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databasefavorite.FavoriteDishesDao
import com.example.foodapp.databasefavorite.FavoriteDishesDatabase
import com.example.foodapp.repositorydishbyid.DishByIdDataModel
import kotlinx.android.synthetic.main.activity_category_list.*
import kotlinx.android.synthetic.main.activity_dish_full_description.*
import kotlinx.android.synthetic.main.activity_dish_full_description.toolbarDishFullDescriptionActivity
import kotlinx.android.synthetic.main.activity_favorite_dishes.*
import kotlinx.android.synthetic.main.item_favorite_dishes.view.*

class FavoriteDishesActivity : AppCompatActivity() {

    private lateinit var favoriteDishesDao: FavoriteDishesDao
    private lateinit var favoriteDishesList: List<DishByIdDataModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_dishes)

        setSupportActionBar(toolbarFavoriteDishes)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val favoriteDishesDatabase = FavoriteDishesDatabase.getDatabaseInstance(this)
        if (favoriteDishesDatabase != null){
            favoriteDishesDao = favoriteDishesDatabase.getFavoriteDishesDao()
        }
    }

    override fun onResume() {
        super.onResume()
        favoriteDishesList = loadDishesList()
        initItemList()
    }

    private fun initItemList() {
        recycleViewFavoriteDishesList.apply {
            adapter = FavoriteDishesActivity.FavoriteDishAdapter(favoriteDishesList, object : OnDishByIdClickListener {
                override fun displayDishById(dishId: String) {
                    startDishFullDescriptionActivity(dishId)
                }
            })
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun startDishFullDescriptionActivity(dishId: String){
        val instance = DishFullDescriptionActivity.newInstance()
        val intent = instance.newIntent(this@FavoriteDishesActivity)
        intent.putExtra(KEY_EXTRA_DISH_ID, dishId)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadDishesList(): List<DishByIdDataModel>{
        return favoriteDishesDao.getAll()
    }

    companion object {
        fun newInstance() = FavoriteDishesActivity()
    }

    fun newIntent(context: Context): Intent {
        return Intent(context, FavoriteDishesActivity::class.java)
    }


    // ADAPTER
    class FavoriteDishAdapter(
        private val itemList: List<DishByIdDataModel>,
        private val onDishByIdClickListener: OnDishByIdClickListener
    ) : RecyclerView.Adapter<FavoriteDishAdapter.FavoriteDishViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteDishViewHolder =
            FavoriteDishViewHolder(
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_dishes, parent, false)
            )

        override fun onBindViewHolder(holder: FavoriteDishViewHolder, position: Int) {
            holder.bind(itemList[position], onDishByIdClickListener)
        }

        override fun getItemCount(): Int = itemList.size

        // ViewHolder
        class FavoriteDishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

            fun bind(dishByIdDataModel: DishByIdDataModel, onDishByIdClickListener: OnDishByIdClickListener){
                itemView.apply {
                    viewDishName.text = dishByIdDataModel.dishName
                    viewCategoryTitle.text = dishByIdDataModel.categoryName
                    viewAreaTitle.text = dishByIdDataModel.areaName
                    setOnClickListener { onDishByIdClickListener.displayDishById(dishByIdDataModel.dishId) }

                    Glide.with(context)
                        .load(dishByIdDataModel.urlToImage)
                        .into(viewItemPhoto)


//                    setOnLongClickListener(View.OnLongClickListener {
//                        Log.d("TAG", "Long click")
//                        true
//                    })
                }
            }
        }
    }
}