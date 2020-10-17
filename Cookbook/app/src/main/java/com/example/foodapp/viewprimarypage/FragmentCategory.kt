package com.example.foodapp.viewprimarypage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.imagemanager.CategoryImageManager
import com.example.foodapp.repositorycategory.CategoryListDataModel
import com.example.foodapp.repositorycategory.CategoryListDataModelMapper
import com.example.foodapp.repositorycategory.CategoryRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.item_category.view.*
import okhttp3.OkHttpClient

class FragmentCategory : Fragment() {

    private var disposable: Disposable? = null
    private var onItemCategoryClickListener: OnItemCategoryClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnItemCategoryClickListener) {
            onItemCategoryClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        onItemCategoryClickListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.fragment_category, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItemList()
    }

    private fun initItemList() {
        recycleViewCategory.apply {
            adapter = CategoryAdapter { categoryName ->
                onItemCategoryClickListener?.displayItemCategory(categoryName)
            }
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onResume() {
        super.onResume()
        fetchCategoryList()
    }

    private fun fetchCategoryList() {
        disposable = CategoryRepositoryImpl(
            okHttpClient = OkHttpClient(),
            categoryListDataModelMapper = CategoryListDataModelMapper()
        ).getCategoryList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list -> (recycleViewCategory.adapter as? CategoryAdapter)?.loadItemList(list) },
                { throwable -> Log.d("FragmentCategory", throwable.toString()) }
            )

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    companion object {
        const val TAG = "FragmentCategory"

        @JvmStatic
        fun newInstance() = FragmentCategory()
    }


    // ADAPTER
    class CategoryAdapter(
        private val actionEvent: (String) -> Unit
    ) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

        private val itemList = mutableListOf<CategoryListDataModel>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
            CategoryViewHolder(
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_category, parent, false),
                actionEvent = actionEvent
            )

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            holder.bind(itemList[position])
        }

        override fun getItemCount(): Int = itemList.size

        fun loadItemList(receivedItemList: List<CategoryListDataModel>) {
            itemList.addAll(receivedItemList)
            notifyDataSetChanged()
        }

        // ViewHolder
        class CategoryViewHolder(
            itemView: View,
            private val actionEvent: (String) -> Unit
        ) : RecyclerView.ViewHolder(itemView) {

            fun bind(categoryListDataModel: CategoryListDataModel) {
                val categoryImageManager = CategoryImageManager.instance
                categoryImageManager.loadImage(categoryListDataModel, itemView.viewItemPhoto)

                itemView.apply {
                    viewCategoryTitle.text = categoryListDataModel.dishCategory
                    setOnClickListener { actionEvent(categoryListDataModel.dishCategory) }
                }
            }
        }
    }
}