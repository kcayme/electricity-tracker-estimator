package com.example.electricitips.fragments


import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.electricitips.R



 class Dashboard: Fragment() {

     override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
         return view
     }

     override fun onViewCreated(view:View, savedInstanceState: Bundle?){
        super.onViewCreated(view,savedInstanceState)
         val list = arrayListOf(
             "Antique ruby",
             "Bitter lemon",
             "Virat Kohli", "Rohit Sharma", "Steve Smith",
             "Kane Williamson", "Ross Taylor"
         )
         var listView = view?.findViewById<ListView>(R.id.listView)
         var adapter: ArrayAdapter<String> = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, list  )

         listView!!.adapter = adapter
     }

}