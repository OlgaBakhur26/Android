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
import com.example.foodapp.imagemanager.AreaImageManager
import com.example.foodapp.repositoryarea.AreaListDataModel
import com.example.foodapp.repositoryarea.AreaListDataModelMapper
import com.example.foodapp.repositoryarea.AreaRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_area.*
import kotlinx.android.synthetic.main.fragment_area.view.viewAreaTitle
import kotlinx.android.synthetic.main.item_area.view.*
import okhttp3.OkHttpClient

class FragmentArea : Fragment() {

    private var disposable: Disposable? = null
    private var onItemAreaClickListener: OnItemAreaClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnItemAreaClickListener) {
            onItemAreaClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        onItemAreaClickListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.fragment_area, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItemList()
    }

    private fun initItemList() {
        recycleViewArea.apply {
            adapter = AreaAdapter { areaName -> onItemAreaClickListener?.displayItemArea(areaName) }
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onResume() {
        super.onResume()
        fetchAreaList()
    }

    private fun fetchAreaList() {
        disposable = AreaRepositoryImpl(
            okHttpClient = OkHttpClient(),
            areaListDataModelMapper = AreaListDataModelMapper()
        ).getAreaList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list -> (recycleViewArea.adapter as? AreaAdapter)?.loadItemList(list) },
                { throwable -> Log.d("FragmentArea", throwable.toString()) }
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    companion object {
        const val TAG = "FragmentArea"

        @JvmStatic
        fun newInstance() = FragmentArea()
    }


    // ADAPTER
    class AreaAdapter(
        private val actionEvent: (String) -> Unit
    ) : RecyclerView.Adapter<AreaAdapter.AreaViewHolder>() {

        private val itemList = mutableListOf<AreaListDataModel>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder =
            AreaViewHolder(
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_area, parent, false),
                actionEvent = actionEvent
            )

        override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
            holder.bind(itemList[position])
        }

        override fun getItemCount(): Int = itemList.size

        fun loadItemList(receivedItemList: List<AreaListDataModel>) {
            itemList.addAll(receivedItemList)
            notifyDataSetChanged()
        }

        // ViewHolder
        class AreaViewHolder(
            itemView: View,
            private val actionEvent: (String) -> Unit
        ) : RecyclerView.ViewHolder(itemView) {

            fun bind(areaListDataModel: AreaListDataModel) {
                val areaImageManager = AreaImageManager.instance
                areaImageManager.loadImage(areaListDataModel, itemView.viewItemFlag)

                itemView.apply {
                    viewAreaTitle.text = areaListDataModel.dishArea
                    setOnClickListener { actionEvent(areaListDataModel.dishArea) }
                }
            }
        }
    }
}