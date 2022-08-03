package com.example.electricitips.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.activityViewModels
import com.example.electricitips.*
import com.example.electricitips.databinding.FragmentHomeBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.formatter.PercentFormatter

class Home :  Fragment(R.layout.fragment_home){
    private var binding: FragmentHomeBinding? = null
    private var arrayList = ArrayList<Appliance>()
    private lateinit var applianceDBHelper: ApplianceDBHelper
    private lateinit var rateDBHelper: RateDBHelper
    var rating: Float = 0.0f

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

        var counter:Int = 0
        var entertainment:Double = 0.0
        var lighting:Double = 0.0
        var cooling:Double = 0.0
        var kitchenApp:Double = 0.0
        var householdApp:Double = 0.0
        var other:Double = 0.0
        var total:Double = 0.0
        var goal:Double = 0.0
        var dummyPrice:Double = 0.0
        var pieChart = binding!!.monthlyUsagePieChart

        for (item in arrayList){

            when(arrayList[counter].frequency){

                "Daily" -> dummyPrice = (arrayList[counter].rating.toDouble()) * (arrayList[counter].duration.toDouble()) * 30
                "Weekly" -> dummyPrice = (arrayList[counter].rating.toDouble()) * (arrayList[counter].duration.toDouble()) * 4
                "Monthly" -> dummyPrice = (arrayList[counter].rating.toDouble()) * (arrayList[counter].duration.toDouble())

            }
            when(arrayList[counter].type){

                "Entertainment" -> entertainment += dummyPrice
                "Lighting" -> lighting += dummyPrice
                "Cooling" -> cooling += dummyPrice
                "Kitchen Appliance" -> kitchenApp += dummyPrice
                "Household Appliance" -> householdApp += dummyPrice
                "Others" -> other += dummyPrice

            }
            counter += 1

        }

        total = entertainment+lighting+cooling+kitchenApp+householdApp+other

        binding!!.setKwhMonthly.setOnClickListener(){

            goal = binding!!.kwhMonthly.text.toString().toDouble()

        }

        val entries = ArrayList<PieEntry>()

        if(entertainment>0){
            entries.add(PieEntry(entertainment.toFloat()))
        }
        if(lighting>0){
            entries.add(PieEntry(lighting.toFloat()))
        }
        if(cooling>0){
            entries.add(PieEntry(cooling.toFloat()))
        }
        if(kitchenApp>0){
            entries.add(PieEntry(kitchenApp.toFloat()))
        }
        if(householdApp>0){
            entries.add(PieEntry(householdApp.toFloat()))
        }
        if(other>0){
            entries.add(PieEntry(other.toFloat()))
        }

        val dataSet = PieDataSet(entries,"")

        dataSet.setDrawValues(false)
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueFormatter = PercentFormatter()

        val colorSet = java.util.ArrayList<Int>()
        colorSet.add(Color.parseColor("#B95051"))
        colorSet.add(Color.parseColor("#64412F"))
        colorSet.add(Color.parseColor("#743D6A"))
        colorSet.add(Color.parseColor("#80519E"))
        colorSet.add(Color.parseColor("#e1a81e"))
        colorSet.add(Color.parseColor("#c23d59"))
        dataSet.colors = colorSet

        val data = PieData(dataSet)

        pieChart.data = data
        pieChart.centerTextRadiusPercent = 0f
        pieChart.isDrawHoleEnabled = false
        pieChart.isUsePercentValuesEnabled
        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = false

        binding!!.dailyEle.text = (total/30).toString() + "kW"
        binding!!.monthlyEle.text = (total).toString() + "kW"

        binding!!.setKwhMonthly.setOnClickListener(){

            if(binding!!.kwhMonthly.text.toString().trim().isNullOrBlank()){

                binding!!.kwhMonthly.error = "Field is Empty."

            }
            else{

                val progress = ((total/binding!!.kwhMonthly.text.toString().toFloat())*100).toInt()
                binding!!.usageLimitProgress.progress = progress
                binding!!.usageLimitText.text = "$progress%"

            }

        }

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

    // binding must be set to null on fragment destroy to prevent memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}