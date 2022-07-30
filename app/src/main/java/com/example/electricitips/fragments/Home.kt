package com.example.electricitips.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.electricitips.R
import com.example.electricitips.SharedViewModel
import com.example.electricitips.databinding.FragmentHomeBinding

class Home :  Fragment(R.layout.fragment_home){
    // this is an instance of SharedViewModel used to get data like electricity rate and item input from dashboard fragment
    // NOT USED FOR DATA PERSISTENCE
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container:ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    fun setPieChartValues(){

        binding.usageLimit

    }

}