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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.repositorydishlistbyarea.DishListByAreaDataModel
import com.example.foodapp.repositorydishlistbyarea.DishListByAreaDataModelMapper
import com.example.foodapp.repositorydishlistbyarea.DishListByAreaRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_area_list.*
import kotlinx.android.synthetic.main.item_area_list.view.*
import okhttp3.OkHttpClient

const val KEY_EXTRA_AREA_NAME = "KEY_EXTRA_AREA_NAME"

class AreaListActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    private lateinit var areaName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_list)

        setSupportActionBar(toolbarAreaListActivity)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        areaName = getAreaName()
        initItemList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getAreaName(): String{
        val intent = intent
        return intent.getStringExtra(KEY_EXTRA_AREA_NAME) as String
    }

    private fun initItemList() {
        recycleViewAreaList.apply {
            adapter = AreaListAdapter(object : OnDishByIdClickListener{
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
        val intent = instance.newIntent(this@AreaListActivity)
        intent.putExtra(KEY_EXTRA_DISH_ID, dishId)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        fetchDishListByArea()
    }

    private fun fetchDishListByArea() {
        disposable = DishListByAreaRepositoryImpl(
            okHttpClient = OkHttpClient(),
            dishListByAreaDataModelMapper = DishListByAreaDataModelMapper()
        ).getDishListByArea(areaName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list -> (recycleViewAreaList.adapter as? AreaListAdapter)?.loadItemList(list) },
                { throwable -> Log.d("AreaListActivity", throwable.toString()) }
            )

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    companion object {
        fun newInstance() = AreaListActivity()
    }

    fun newIntent(context: Context): Intent {
        return Intent(context, AreaListActivity::class.java)
    }



    // ADAPTER
    class AreaListAdapter(
        private val onDishByIdClickListener: OnDishByIdClickListener
    ) : RecyclerView.Adapter<AreaListAdapter.AreaListViewHolder>(){

        private val itemList = mutableListOf<DishListByAreaDataModel>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaListViewHolder =
            AreaListViewHolder(
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_area_list, parent, false)
            )


        override fun onBindViewHolder(holder: AreaListViewHolder, position: Int) {
            holder.bind(itemList[position], onDishByIdClickListener)
        }

        override fun getItemCount(): Int = itemList.size

        fun loadItemList(receivedItemList: List<DishListByAreaDataModel>) {
            itemList.addAll(receivedItemList)
            notifyDataSetChanged()
        }

        // ViewHolder
        class AreaListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

            fun bind(dishListByAreaDataModel: DishListByAreaDataModel, onDishByIdClickListener: OnDishByIdClickListener){
                itemView.apply {
                    viewDishName.text = dishListByAreaDataModel.dishName
                    setOnClickListener { onDishByIdClickListener.displayDishById(dishListByAreaDataModel.dishId) }

                    Glide.with(context)
                        .load(dishListByAreaDataModel.urlToImage)
                        .into(viewItemFlag)
                }
            }
        }
    }
}