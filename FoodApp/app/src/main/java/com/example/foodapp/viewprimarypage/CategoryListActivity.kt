package com.example.foodapp.viewprimarypage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.repositorydishlistbycategory.DishListByCategoryDataModel
import com.example.foodapp.repositorydishlistbycategory.DishListByCategoryDataModelMapper
import com.example.foodapp.repositorydishlistbycategory.DishListByCategoryRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_category_list.*
import kotlinx.android.synthetic.main.item_category_list.view.*
import okhttp3.OkHttpClient

const val KEY_EXTRA_CATEGORY_NAME = "KEY_PUT_EXTRA_CATEGORY_NAME"

class CategoryListActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    private lateinit var categoryName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list)

        setSupportActionBar(toolbarCategoryListActivity)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        categoryName = getCategoryName()
        initItemList()
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

    private fun getCategoryName(): String{
        val intent = intent
        return intent.getStringExtra(KEY_EXTRA_CATEGORY_NAME) as String
    }

    private fun initItemList() {
        recycleViewCategoryList.apply {
            adapter = CategoryListAdapter(object : OnDishByIdClickListener{
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
        val intent = instance.newIntent(this@CategoryListActivity)
        intent.putExtra(KEY_EXTRA_DISH_ID, dishId)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        fetchDishListByCategory()
    }

    private fun fetchDishListByCategory() {
        disposable = DishListByCategoryRepositoryImpl(
            okHttpClient = OkHttpClient(),
            dishListByCategoryDataModelMapper = DishListByCategoryDataModelMapper()
        ).getDishListByCategory(categoryName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list -> (recycleViewCategoryList.adapter as? CategoryListAdapter)?.loadItemList(list) },
                { throwable -> Log.d("CategoryListActivity", throwable.toString()) }
            )

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    companion object {
        fun newInstance() = CategoryListActivity()
    }

    fun newIntent(context: Context): Intent{
        return Intent(context, CategoryListActivity::class.java)
    }



    // ADAPTER
    class CategoryListAdapter(
        private val onDishByIdClickListener: OnDishByIdClickListener
    ) : RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder>(){

        private val itemList = mutableListOf<DishListByCategoryDataModel>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder =
            CategoryListViewHolder(
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_category_list, parent, false)
            )

        override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
            holder.bind(itemList[position], onDishByIdClickListener)
        }

        override fun getItemCount(): Int = itemList.size

        fun loadItemList(receivedItemList: List<DishListByCategoryDataModel>) {
            itemList.addAll(receivedItemList)
            notifyDataSetChanged()
        }

        // ViewHolder
        class CategoryListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

            fun bind(dishListByCategoryDataModel: DishListByCategoryDataModel, onDishByIdClickListener: OnDishByIdClickListener){
                itemView.apply {
                    viewDishName.text = dishListByCategoryDataModel.dishName

                    Glide.with(context)
                        .load(dishListByCategoryDataModel.urlToImage)
                        .into(viewItemPhoto)

                    setOnClickListener { onDishByIdClickListener.displayDishById(dishListByCategoryDataModel.dishId) }
                }
            }
        }
    }
}