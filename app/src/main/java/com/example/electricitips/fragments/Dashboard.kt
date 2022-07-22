package com.example.electricitips.fragments


import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.ui.AppBarConfiguration
import com.example.electricitips.R
import com.example.electricitips.displayView



 class Dashboard: Fragment() {

     var displayView: displayView = displayView()

     fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {

         val view = displayView.onCreateView(inflater, container, savedInstanceState)
         return view

     }

}