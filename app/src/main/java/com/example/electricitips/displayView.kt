package com.example.electricitips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

open class displayView: AppCompatActivity() {

        fun onCreateView(
            inflater: LayoutInflater, container:ViewGroup?, saveInstanceStatus:Bundle
        ): View? {
            val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
            val list = arrayListOf(
                "Antique ruby",
                "Bitter lemon",
                "Virat Kohli", "Rohit Sharma", "Steve Smith",
                "Kane Williamson", "Ross Taylor"
            )
            val adapterlist: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)

            view.findViewById<ListView>(R.id.listView).adapter = adapterlist

            return view
        }




}
