package com.praham.bookhub.bookhub.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.praham.bookhub.R

// TODO: Rename parameter arguments, choose names that match

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this com.praham.bookhub.activity.fragment
        val view=inflater.inflate(R.layout.fragment_about, container, false)
        return view
    }


}