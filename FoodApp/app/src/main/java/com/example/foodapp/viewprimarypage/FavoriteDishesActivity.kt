package com.example.foodapp.viewprimarypage

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databasefavorite.FavoriteDishesDao
import com.example.foodapp.databasefavorite.FavoriteDishesDatabase
import com.example.foodapp.repositorydishbyid.DishByIdDataModel
import kotlinx.android.synthetic.main.activity_favorite_dishes.*
import kotlinx.android.synthetic.main.item_favorite_dishes.view.*

class FavoriteDishesActivity : AppCompatActivity() {

    private lateinit var favoriteDishesDao: FavoriteDishesDao
    private lateinit var favoriteDishesList: MutableList<DishByIdDataModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_dishes)

        setSupportActionBar(toolbarFavoriteDishes)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteDishesList = mutableListOf()

        val favoriteDishesDatabase = FavoriteDishesDatabase.getDatabaseInstance(this)
        if (favoriteDishesDatabase != null) {
            favoriteDishesDao = favoriteDishesDatabase.getFavoriteDishesDao()
        }
    }

    override fun onResume() {
        super.onResume()
        loadDishesList()
        initItemList()
    }

    private fun initItemList() {
        recycleViewFavoriteDishesList.apply {
            adapter = FavoriteDishAdapter(favoriteDishesList, object : OnDishByIdClickListener {
                override fun displayDishById(dishId: String) {
                    startDishFullDescriptionActivity(dishId)
                }
            }, object : OnDishByIdLongClickListener {
                override fun deleteDishById(dishId: String) {
                    showDialogDeleteFavorite(dishId)
                }

            })
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun startDishFullDescriptionActivity(dishId: String) {
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

    private fun loadDishesList() {
        favoriteDishesDao.getAll().observe(this, Observer { list ->
            (recycleViewFavoriteDishesList.adapter as? FavoriteDishAdapter)?.updateItemList(list)
        })
    }

    private fun showDialogDeleteFavorite(dishId: String) {
        val dishToDelete = favoriteDishesDao.getByIdGeneral(dishId)
        val dishName = dishToDelete.dishName
        val dialog = AlertDialog.Builder(
            this@FavoriteDishesActivity,
            R.style.Widget_AppCompat_ButtonBar_AlertDialog
        )
        dialog
            .setTitle(R.string.deleteDishDialogTitle)
            .setMessage(dishName)
            .setPositiveButton(getString(R.string.dialogDeleteDishPositiveButton),
                DialogInterface.OnClickListener { dialog, which ->
                    favoriteDishesDao.delete(dishToDelete)
                })
            .setNegativeButton(getString(R.string.dialogDeleteDishNegativeButton),
                DialogInterface.OnClickListener { dialog, which ->
                })
            .create()
        dialog.show()
    }

    companion object {
        fun newInstance() = FavoriteDishesActivity()
    }

    fun newIntent(context: Context): Intent {
        return Intent(context, FavoriteDishesActivity::class.java)
    }


    // ADAPTER
    class FavoriteDishAdapter(
        private val itemList: MutableList<DishByIdDataModel>,
        private val onDishByIdClickListener: OnDishByIdClickListener,
        private val onDishByIdLongClickListener: OnDishByIdLongClickListener
    ) : RecyclerView.Adapter<FavoriteDishAdapter.FavoriteDishViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteDishViewHolder =
            FavoriteDishViewHolder(
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_favorite_dishes, parent, false)
            )

        override fun onBindViewHolder(holder: FavoriteDishViewHolder, position: Int) {
            holder.bind(itemList[position], onDishByIdClickListener, onDishByIdLongClickListener)
        }

        override fun getItemCount(): Int = itemList.size

        fun updateItemList(itemListIn: List<DishByIdDataModel>) {
            itemList.apply {
                clear()
                addAll(itemListIn)
            }
            notifyDataSetChanged()
        }

        // ViewHolder
        class FavoriteDishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(
                dishByIdDataModel: DishByIdDataModel,
                onDishByIdClickListener: OnDishByIdClickListener,
                onDishByIdLongClickListener: OnDishByIdLongClickListener
            ) {
                itemView.apply {
                    viewDishName.text = dishByIdDataModel.dishName
                    viewCategoryTitle.text = dishByIdDataModel.categoryName
                    viewAreaTitle.text = dishByIdDataModel.areaName
                    setOnClickListener { onDishByIdClickListener.displayDishById(dishByIdDataModel.dishId) }

                    Glide.with(context)
                        .load(dishByIdDataModel.urlToImage)
                        .into(viewItemPhoto)

                    setOnLongClickListener {
                        onDishByIdLongClickListener.deleteDishById(dishByIdDataModel.dishId)
                        return@setOnLongClickListener true
                    }
                }
            }
        }
    }
}