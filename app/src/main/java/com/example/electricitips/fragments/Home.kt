package com.example.electricitips.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.electricitips.*
import com.example.electricitips.databinding.FragmentHomeBinding

class Home :  Fragment(R.layout.fragment_home){
    private var binding: FragmentHomeBinding? = null
    private var arrayList = ArrayList<Appliance>()
    private lateinit var applianceDBHelper: ApplianceDBHelper
    private lateinit var rateDBHelper: RateDBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container:ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        // initialize db helpers
        applianceDBHelper = ApplianceDBHelper(requireActivity().applicationContext)
        rateDBHelper = RateDBHelper(requireActivity().applicationContext)

        arrayList = applianceDBHelper.readAllAppliances()


        return binding!!.root
    }

    // binding must be set to null on fragment destroy to prevent memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}