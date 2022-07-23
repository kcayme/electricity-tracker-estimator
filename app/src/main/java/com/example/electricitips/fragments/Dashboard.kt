package com.example.electricitips.fragments


import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.electricitips.MyData
import com.example.electricitips.MyListAdapter
import com.example.electricitips.R
import com.example.electricitips.databinding.FragmentDashboardBinding

import com.example.electricitips.databinding.FragmentDashboardBinding

class Dashboard :  Fragment(R.layout.fragment_dashboard){
    private val romImgIds = arrayOf<Int>(R.drawable.ic_appliance,R.drawable.ic_appliance,R.drawable.ic_appliance,R.drawable.ic_appliance,R.drawable.ic_appliance)
    private val romanceTitles = arrayOf<String>("About Time", "Pride & Prejudice", "The Fault in Our Stars", "Crazy Rich Asians", "Me Before You")
    private val romanceRelease = arrayOf<String>("2013","2005","2014","2018","2016")

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
    var arrayList: ArrayList<MyData> = ArrayList()
    private var _binding: FragmentDashboardBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrayList.add(MyData(R.drawable.ic_appliance, "About Time",  "2013"))
        arrayList.add(MyData(R.drawable.ic_appliance, "Pride & Prejudice",  "2005"))
        val romListAdapter = MyListAdapter(this, arrayList)
        _binding?.deviceList?.adapter = romListAdapter
        if(arguments != null){
            val bundle = arguments
            val data = bundle!!.getStringArrayList("data")
            _binding!!.listHeader.text = data.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}