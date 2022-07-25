package com.example.electricitips.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.electricitips.Appliance
import com.example.electricitips.ApplianceDBHelper
import com.example.electricitips.R
import com.example.electricitips.SharedViewModel
import com.example.electricitips.databinding.FragmentHomeBinding

class Home :  Fragment(R.layout.fragment_home){
    // this is an instance of SharedViewModel used to get data like electricity rate and item input from dashboard fragment
    // NOT USED FOR DATA PERSISTENCE
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var binding: FragmentHomeBinding? = null
    private var arrayList = ArrayList<Appliance>()
    private lateinit var applianceDBHelper: ApplianceDBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container:ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        // initialize db helper
        applianceDBHelper = ApplianceDBHelper(activity!!.applicationContext)
        arrayList = applianceDBHelper.readAllAppliances()

        binding!!.itemsInputTest.text = arrayList.size.toString()
        /*
        // access electricity cost rate saved in the view model
        sharedViewModel.electricityRateLive.observe(viewLifecycleOwner) { electricityrate ->
            binding!!.rateInputTest.text = electricityrate.toString()
        }

        // access arraylist of items saved in the view model
        sharedViewModel.arrayListLive.observe(viewLifecycleOwner){arrayList ->
            binding!!.itemsInputTest.text = arrayList.size.toString()
        }*/

        return binding!!.root
    }

}