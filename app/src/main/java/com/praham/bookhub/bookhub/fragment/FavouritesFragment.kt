package com.praham.bookhub.bookhub.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.praham.bookhub.R

// TODO: Rename parameter arguments, choose names that match

class FavouritesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this com.praham.bookhub.activity.fragment
        val view =inflater.inflate(R.layout.fragment_favourites, container, false)
        return view
    }

}