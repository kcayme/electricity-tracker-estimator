package com.example.electricitips.fragments


import android.content.Context
import android.media.MediaPlayer
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.electricitips.*
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

}
/*private lateinit var binding: FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }*/


    //private var arrayList = ArrayList<Appliance>()
    //private var filteredArrayList = ArrayList<Appliance>()
    //private lateinit var applianceDBHelper: ApplianceDBHelper
    //private lateinit var rateDBHelper: RateDBHelper



        // initialize db helpers
        /*applianceDBHelper = ApplianceDBHelper(activity!!.applicationContext)
         rateDBHelper = RateDBHelper(activity!!.applicationContext)

         binding.inputCostRate.setText(rateDBHelper.readCost().toString())

         binding.setRateBtn.setOnClickListener {
             val mSet = MediaPlayer.create(context,R.raw.set)
             rateDBHelper.deleteCost()
             val cost = binding.inputCostRate.text.toString().toFloat()
             rateDBHelper.insertRate(cost)
             val test = rateDBHelper.readCost()
             mSet.start()
             // hide keyboard layout after set button is pressed
             val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
             imm.hideSoftInputFromWindow(binding.costInputLayout.windowToken,0)
             Toast.makeText(context, "Electricity rate set! $test",Toast.LENGTH_SHORT).show()
         }*/





        /*arrayList = applianceDBHelper.readAllAppliances()

        if(arrayList.size == 0){
            arrayList.add(Appliance(R.drawable.empty,"No items to show","","",0.0f,0.0f,""))
        }
        // setup adapter for card view inside the recycler view
        val cardAdapter = RecyclerViewAdapter(arrayList, this)
        binding.dashboardRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.dashboardRecyclerview.adapter = cardAdapter

        binding.filterEntertainment.setOnClickListener {
            filterByEntertainment()
        }

        binding.filterLighting.setOnClickListener {
            filterByLighting()
        }

        binding.filterCooling.setOnClickListener {
            filterByCooling()
        }

        binding.filterKitchen.setOnClickListener {
            filterByKitchen()
        }

        binding.filterHousehold.setOnClickListener {
            filterByHousehold()
        }

        binding.filterOthers.setOnClickListener {
            filterByOthers()
        }*/




    /*private fun filterByOthers() {
        var cardAdapter: RecyclerViewAdapter? = null
        val checkedIDs = binding.chipGroup.checkedChipIds
        //if no chips are selected
        if (!binding.filterOthers.isChecked && checkedIDs.size == 0) {
            cardAdapter = RecyclerViewAdapter(arrayList, this)
            filteredArrayList.clear()
        }
        // if this chip is not selected but other chips are
        else if (!binding.filterOthers.isChecked && checkedIDs.size >= 1) {
            filteredArrayList.removeIf {
                it.type == "Others"
            }
            cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
        }
        // if this chip is selected
        else if (binding.filterOthers.isChecked) {
            for (appliance in arrayList) {
                if (appliance.type == "Others") {
                    filteredArrayList.add(appliance)
                }
            }
            cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
        }
        binding.dashboardRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.dashboardRecyclerview.adapter = cardAdapter
    }

    private fun filterByHousehold() {
        var cardAdapter: RecyclerViewAdapter? = null
        val checkedIDs = binding.chipGroup.checkedChipIds
        //if no chips are selected
        if (!binding.filterHousehold.isChecked && checkedIDs.size == 0) {
            cardAdapter = RecyclerViewAdapter(arrayList, this)
            filteredArrayList.clear()
        }
        // if this chip is not selected but other chips are
        else if (!binding.filterHousehold.isChecked && checkedIDs.size >= 1) {
            filteredArrayList.removeIf {
                it.type == "Household Appliance"
            }
            cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
        }
        // if this chip is selected
        else if (binding.filterHousehold.isChecked) {
            for (appliance in arrayList) {
                if (appliance.type == "Household Appliance") {
                    filteredArrayList.add(appliance)
                }
            }
            cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
        }
        binding.dashboardRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.dashboardRecyclerview.adapter = cardAdapter
    }

    private fun filterByKitchen() {
        var cardAdapter: RecyclerViewAdapter? = null
        val checkedIDs = binding.chipGroup.checkedChipIds
        //if no chips are selected
        if (!binding.filterCooling.isChecked && checkedIDs.size == 0) {
            cardAdapter = RecyclerViewAdapter(arrayList, this)
            filteredArrayList.clear()
        }
        // if this chip is not selected but other chips are
        else if (!binding.filterKitchen.isChecked && checkedIDs.size >= 1) {
            filteredArrayList.removeIf {
                it.type == "Kitchen Appliance"
            }
            cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
        }
        // if this chip is selected
        else if (binding.filterKitchen.isChecked) {
            for (appliance in arrayList) {
                if (appliance.type == "Kitchen Appliance") {
                    filteredArrayList.add(appliance)
                }
            }
            cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
        }
        binding.dashboardRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.dashboardRecyclerview.adapter = cardAdapter
    }

    private fun filterByCooling() {
        var cardAdapter: RecyclerViewAdapter? = null
        val checkedIDs = binding.chipGroup.checkedChipIds
        //if no chips are selected
        if (!binding.filterCooling.isChecked && checkedIDs.size == 0) {
            cardAdapter = RecyclerViewAdapter(arrayList, this)
            filteredArrayList.clear()
        }
        // if this chip is not selected but other chips are
        else if (!binding.filterCooling.isChecked && checkedIDs.size >= 1) {
            filteredArrayList.removeIf {
                it.type == "Cooling"
            }
            cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
        }
        // if this chip is selected
        else if (binding.filterCooling.isChecked) {
            for (appliance in arrayList) {
                if (appliance.type == "Cooling") {
                    filteredArrayList.add(appliance)
                }
            }
            cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
        }
        binding.dashboardRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.dashboardRecyclerview.adapter = cardAdapter
    }

    private fun filterByLighting() {
        var cardAdapter: RecyclerViewAdapter? = null
        val checkedIDs = binding.chipGroup.checkedChipIds
        //if no chips are selected
        if (!binding.filterLighting.isChecked && checkedIDs.size == 0) {
            cardAdapter = RecyclerViewAdapter(arrayList, this)
            filteredArrayList.clear()
        }
        // if this chip is not selected but other chips are
        else if (!binding.filterLighting.isChecked && checkedIDs.size >= 1) {
            filteredArrayList.removeIf {
                it.type == "Lighting"
            }
            cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
        }
        // if this chip is selected
        else if (binding.filterLighting.isChecked) {
            for (appliance in arrayList) {
                if (appliance.type == "Lighting") {
                    filteredArrayList.add(appliance)
                }
            }
            cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
        }
        binding.dashboardRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.dashboardRecyclerview.adapter = cardAdapter
    }

    private fun filterByEntertainment() {
        var cardAdapter: RecyclerViewAdapter? = null
        val checkedIDs = binding.chipGroup.checkedChipIds
        //if no chips are selected
        if (!binding.filterEntertainment.isChecked && checkedIDs.size == 0) {
            cardAdapter = RecyclerViewAdapter(arrayList, this)
            filteredArrayList.clear()
        }
        // if this chip is not selected but other chips are
        else if (!binding.filterEntertainment.isChecked && checkedIDs.size >= 1) {
            filteredArrayList.removeIf {
                it.type == "Entertainment"
            }
            cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
        }
        // if this chip is selected
        else if (binding.filterEntertainment.isChecked) {
            for (appliance in arrayList) {
                if (appliance.type == "Entertainment") {
                    filteredArrayList.add(appliance)
                }
            }
            cardAdapter = RecyclerViewAdapter(filteredArrayList, this)
        }
        binding.dashboardRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.dashboardRecyclerview.adapter = cardAdapter
    }*/

