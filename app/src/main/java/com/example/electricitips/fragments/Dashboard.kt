package com.example.electricitips.fragments


import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.electricitips.R
import com.example.electricitips.databinding.FragmentDashboardBinding


class Dashboard: Fragment() {

     private lateinit var binding: FragmentDashboardBinding

     override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         binding = FragmentDashboardBinding.inflate(inflater, container, false)
         return binding.root
     }

     override fun onViewCreated(view:View, savedInstanceState: Bundle?){
        super.onViewCreated(view,savedInstanceState)
         val list = arrayListOf(
             "Antique ruby",
             "Bitter lemon",
             "Virat Kohli", "Rohit Sharma", "Steve Smith",
             "Kane Williamson", "Ross Taylor"
         )

         var adapter: ArrayAdapter<String> = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, list)

         binding.listView.adapter = adapter
     }

}