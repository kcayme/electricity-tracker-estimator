package com.example.electricitips.fragments


import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.electricitips.Appliance
import com.example.electricitips.R
import com.example.electricitips.RecyclerViewAdapter
import com.example.electricitips.databinding.FragmentDashboardBinding




class Dashboard: Fragment(R.layout.fragment_dashboard) {

    private var binding: FragmentDashboardBinding? = null
    private var arrayList = ArrayList<Appliance>()
    private var filteredArrayList = ArrayList<Appliance>()

     override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         binding = FragmentDashboardBinding.inflate(inflater, container, false)
         return binding!!.root
     }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get data passed from main activity
        if(arguments != null){
            val bundle = arguments
            if (bundle != null) {
                arrayList = bundle.getParcelableArrayList("data")!!
                Toast.makeText(context, "${arrayList.size.toString()}", Toast.LENGTH_SHORT).show()
            }
        }
        // use data passed into cardview
        if(arrayList != null){
            // setup adapter for card view inside the recycler view
            val cardAdapter = RecyclerViewAdapter(arrayList, this)
            binding?.dashboardRecyclerview?.layoutManager = LinearLayoutManager(context)
            binding?.dashboardRecyclerview?.adapter = cardAdapter
        }

        binding!!.chip1.setOnClickListener {

            var cardAdapter: RecyclerViewAdapter? = null
            val checkedIDs = binding!!.chipGroup.checkedChipIds
            //if no chips are selected
            if(!binding!!.chip1.isChecked && checkedIDs.size == 0){
                cardAdapter = RecyclerViewAdapter(arrayList, this)
                filteredArrayList.clear()
            }
            // if this chip is not selected but other chips are
            else if(!binding!!.chip1.isChecked && checkedIDs.size >= 1){
                filteredArrayList.removeIf {
                    it.type == "Entertainment"
                }
                cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
            }
            // if this chip is selected
            else if(binding!!.chip1.isChecked) {
                for (appliance in arrayList){
                    if(appliance.type == "Entertainment"){
                        filteredArrayList.add(appliance)
                    }
                }
                cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
            }
            binding?.dashboardRecyclerview?.layoutManager = LinearLayoutManager(context)
            binding?.dashboardRecyclerview?.adapter = cardAdapter
        }

        binding!!.chip2.setOnClickListener {
            var cardAdapter: RecyclerViewAdapter? = null
            val checkedIDs = binding!!.chipGroup.checkedChipIds
            //if no chips are selected
            if(!binding!!.chip2.isChecked && checkedIDs.size == 0){
                cardAdapter = RecyclerViewAdapter(arrayList, this)
                filteredArrayList.clear()
            }
            // if this chip is not selected but other chips are
            else if(!binding!!.chip2.isChecked && checkedIDs.size >= 1){
                filteredArrayList.removeIf {
                    it.type == "Lighting"
                }
                cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
            }
            // if this chip is selected
            else if(binding!!.chip2.isChecked) {
                for (appliance in arrayList){
                    if(appliance.type == "Lighting"){
                        filteredArrayList.add(appliance)
                    }
                }
                cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
            }
            binding?.dashboardRecyclerview?.layoutManager = LinearLayoutManager(context)
            binding?.dashboardRecyclerview?.adapter = cardAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}