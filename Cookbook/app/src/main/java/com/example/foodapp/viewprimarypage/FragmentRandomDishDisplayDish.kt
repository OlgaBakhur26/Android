package com.example.foodapp.viewprimarypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.repositoryrandom.RandomDishDataModelMapper
import com.example.foodapp.repositoryrandom.RandomDishRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_random_dish_display_dish.*
import okhttp3.OkHttpClient

class FragmentRandomDishDisplayDish : Fragment() {

    private var disposable: Disposable? = null
    private lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_random_dish_display_dish, container, false)

    override fun onResume() {
        super.onResume()
        fetchRandomDish()

        viewRandomDish.setOnClickListener { startDishFullDescriptionActivity(id) }
    }

    private fun startDishFullDescriptionActivity(dishId: String) {
        val instance = DishFullDescriptionActivity.newInstance()
        val intent = this@FragmentRandomDishDisplayDish.context?.let { instance.newIntent(it) }
        intent?.putExtra(KEY_EXTRA_DISH_ID, dishId)
        startActivity(intent)
    }

    private fun fetchRandomDish() {
        disposable = RandomDishRepositoryImpl(
            okHttpClient = OkHttpClient(),
            randomDishDataModelMapper = RandomDishDataModelMapper()
        ).getRandomDish()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { dish ->
                    with(dish) {
                        viewDishName.text = dishName
                        viewCategoryTitle.text = dishCategory
                        viewAreaTitle.text = dishArea
                        viewIngredientsList.text = dishIngredients

                        this@FragmentRandomDishDisplayDish.context?.let {
                            Glide.with(it)
                                .load(urlToImage)
                                .into(viewItemPhoto)
                        }
                    }
                    this.id = dish.dishId
                },
                { throwable -> Log.d("FragmentRandomDishDisplayDish", throwable.toString()) }
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    companion object {
        const val TAG = "FragmentRandomDishDisplayDish"

        @JvmStatic
        fun newInstance() = FragmentRandomDishDisplayDish()
    }
}