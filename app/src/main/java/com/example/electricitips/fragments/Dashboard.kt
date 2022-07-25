package com.example.electricitips.fragments


import android.content.Context
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.electricitips.Appliance
import com.example.electricitips.R
import com.example.electricitips.RecyclerViewAdapter
import com.example.electricitips.SharedViewModel
import com.example.electricitips.databinding.FragmentDashboardBinding




class Dashboard: Fragment(R.layout.fragment_dashboard) {

    private var binding: FragmentDashboardBinding? = null
    private var arrayList = ArrayList<Appliance>()
    private var filteredArrayList = ArrayList<Appliance>()
    // this is a ViewModel class that holds data (the added items and electricity rate input) that can be used by all fragments and main activity
    // NOT USED FOR DATA PERSISTENCE
    private val sharedViewModel: SharedViewModel by activityViewModels()

     override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         binding = FragmentDashboardBinding.inflate(inflater, container, false)

         val modelSavedCost = sharedViewModel.electricityRateLive.value
         if(modelSavedCost == null){
             binding!!.inputCostRate.setText("0.0")
         }
         else{
             binding!!.inputCostRate.setText(sharedViewModel.electricityRateLive.value.toString())
         }

         binding!!.setRateBtn.setOnClickListener {
             sharedViewModel.setElectrticityRate(binding!!.inputCostRate.text.toString().toFloat())
             // hide keyboard layout after set button is pressed
             val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
             imm.hideSoftInputFromWindow(binding!!.costInputLayout.windowToken,0)
             Toast.makeText(context, "Electricity rate set!",Toast.LENGTH_SHORT).show()
         }
         return binding!!.root
     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get arraylist data passed from main activity
        if(arguments != null){
            val bundle = arguments
            if (bundle != null) {
                arrayList = bundle.getParcelableArrayList("data")!!
            }
        }
        // set arraylist data of sharedviewmodel
        sharedViewModel.setApplianceList(arrayList)

        // use data passed into cardview
        if(arrayList != null){
            // setup adapter for card view inside the recycler view
            val cardAdapter = RecyclerViewAdapter(arrayList, this)
            binding?.dashboardRecyclerview?.layoutManager = LinearLayoutManager(context)
            binding?.dashboardRecyclerview?.adapter = cardAdapter

        }

        binding!!.filterEntertainment.setOnClickListener {
            filterByEntertainment()
        }

        binding!!.filterLighting.setOnClickListener {
            filterByLighting()
        }

        binding!!.filterCooling.setOnClickListener {
            filterByCooling()
        }

        binding!!.filterKitchen.setOnClickListener {
            filterByKitchen()
        }

        binding!!.filterHousehold.setOnClickListener {
            filterByHousehold()
        }

        binding!!.filterOthers.setOnClickListener {
            filterByOthers()
        }

    }

    private fun filterByOthers() {
        if (arrayList != null) {
            var cardAdapter: RecyclerViewAdapter? = null
            val checkedIDs = binding!!.chipGroup.checkedChipIds
            //if no chips are selected
            if (!binding!!.filterOthers.isChecked && checkedIDs.size == 0) {
                cardAdapter = RecyclerViewAdapter(arrayList, this)
                filteredArrayList.clear()
            }
            // if this chip is not selected but other chips are
            else if (!binding!!.filterOthers.isChecked && checkedIDs.size >= 1) {
                filteredArrayList.removeIf {
                    it.type == "Others"
                }
                cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
            }
            // if this chip is selected
            else if (binding!!.filterOthers.isChecked) {
                for (appliance in arrayList) {
                    if (appliance.type == "Others") {
                        filteredArrayList.add(appliance)
                    }
                }
                cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
            }
            binding?.dashboardRecyclerview?.layoutManager = LinearLayoutManager(context)
            binding?.dashboardRecyclerview?.adapter = cardAdapter
        }
    }

    private fun filterByHousehold() {
        if (arrayList != null) {
            var cardAdapter: RecyclerViewAdapter? = null
            val checkedIDs = binding!!.chipGroup.checkedChipIds
            //if no chips are selected
            if (!binding!!.filterHousehold.isChecked && checkedIDs.size == 0) {
                cardAdapter = RecyclerViewAdapter(arrayList, this)
                filteredArrayList.clear()
            }
            // if this chip is not selected but other chips are
            else if (!binding!!.filterHousehold.isChecked && checkedIDs.size >= 1) {
                filteredArrayList.removeIf {
                    it.type == "Household Appliance"
                }
                cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
            }
            // if this chip is selected
            else if (binding!!.filterHousehold.isChecked) {
                for (appliance in arrayList) {
                    if (appliance.type == "Household Appliance") {
                        filteredArrayList.add(appliance)
                    }
                }
                cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
            }
            binding?.dashboardRecyclerview?.layoutManager = LinearLayoutManager(context)
            binding?.dashboardRecyclerview?.adapter = cardAdapter
        }
    }

    private fun filterByKitchen() {
        if (arrayList != null) {
            var cardAdapter: RecyclerViewAdapter? = null
            val checkedIDs = binding!!.chipGroup.checkedChipIds
            //if no chips are selected
            if (!binding!!.filterCooling.isChecked && checkedIDs.size == 0) {
                cardAdapter = RecyclerViewAdapter(arrayList, this)
                filteredArrayList.clear()
            }
            // if this chip is not selected but other chips are
            else if (!binding!!.filterKitchen.isChecked && checkedIDs.size >= 1) {
                filteredArrayList.removeIf {
                    it.type == "Kitchen Appliance"
                }
                cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
            }
            // if this chip is selected
            else if (binding!!.filterKitchen.isChecked) {
                for (appliance in arrayList) {
                    if (appliance.type == "Kitchen Appliance") {
                        filteredArrayList.add(appliance)
                    }
                }
                cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
            }
            binding?.dashboardRecyclerview?.layoutManager = LinearLayoutManager(context)
            binding?.dashboardRecyclerview?.adapter = cardAdapter
        }
    }

    private fun filterByCooling() {
        if (arrayList != null) {
            var cardAdapter: RecyclerViewAdapter? = null
            val checkedIDs = binding!!.chipGroup.checkedChipIds
            //if no chips are selected
            if (!binding!!.filterCooling.isChecked && checkedIDs.size == 0) {
                cardAdapter = RecyclerViewAdapter(arrayList, this)
                filteredArrayList.clear()
            }
            // if this chip is not selected but other chips are
            else if (!binding!!.filterCooling.isChecked && checkedIDs.size >= 1) {
                filteredArrayList.removeIf {
                    it.type == "Cooling"
                }
                cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
            }
            // if this chip is selected
            else if (binding!!.filterCooling.isChecked) {
                for (appliance in arrayList) {
                    if (appliance.type == "Cooling") {
                        filteredArrayList.add(appliance)
                    }
                }
                cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
            }
            binding?.dashboardRecyclerview?.layoutManager = LinearLayoutManager(context)
            binding?.dashboardRecyclerview?.adapter = cardAdapter
        }
    }

    private fun filterByLighting() {
        if (arrayList != null) {
            var cardAdapter: RecyclerViewAdapter? = null
            val checkedIDs = binding!!.chipGroup.checkedChipIds
            //if no chips are selected
            if (!binding!!.filterLighting.isChecked && checkedIDs.size == 0) {
                cardAdapter = RecyclerViewAdapter(arrayList, this)
                filteredArrayList.clear()
            }
            // if this chip is not selected but other chips are
            else if (!binding!!.filterLighting.isChecked && checkedIDs.size >= 1) {
                filteredArrayList.removeIf {
                    it.type == "Lighting"
                }
                cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
            }
            // if this chip is selected
            else if (binding!!.filterLighting.isChecked) {
                for (appliance in arrayList) {
                    if (appliance.type == "Lighting") {
                        filteredArrayList.add(appliance)
                    }
                }
                cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
            }
            binding?.dashboardRecyclerview?.layoutManager = LinearLayoutManager(context)
            binding?.dashboardRecyclerview?.adapter = cardAdapter
        }
    }

    private fun filterByEntertainment() {
        if (arrayList != null) {
            var cardAdapter: RecyclerViewAdapter? = null
            val checkedIDs = binding!!.chipGroup.checkedChipIds
            //if no chips are selected
            if (!binding!!.filterEntertainment.isChecked && checkedIDs.size == 0) {
                cardAdapter = RecyclerViewAdapter(arrayList, this)
                filteredArrayList.clear()
            }
            // if this chip is not selected but other chips are
            else if (!binding!!.filterEntertainment.isChecked && checkedIDs.size >= 1) {
                filteredArrayList.removeIf {
                    it.type == "Entertainment"
                }
                cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
            }
            // if this chip is selected
            else if (binding!!.filterEntertainment.isChecked) {
                for (appliance in arrayList) {
                    if (appliance.type == "Entertainment") {
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