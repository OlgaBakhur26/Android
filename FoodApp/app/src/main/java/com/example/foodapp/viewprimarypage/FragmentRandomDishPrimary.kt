package com.example.foodapp.viewprimarypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodapp.R

class FragmentRandomDishPrimary : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_random_dish_primary, container, false)


    companion object {
        const val TAG = "FragmentRandomDishPrimary"

        @JvmStatic
        fun newInstance() = FragmentRandomDishPrimary()
    }


}