package com.example.electricitips.fragments

import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.electricitips.*
import com.example.electricitips.databinding.FragmentHomeBinding
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.PieData
import java.util.*
import kotlin.collections.ArrayList

class Home :  Fragment(R.layout.fragment_home){
    private var binding: FragmentHomeBinding? = null
    private var arrayList = ArrayList<Appliance>()
    private var rateCost:Float = 0.0F
    private var max: Float = 0.0F
    private lateinit var applianceDBHelper: ApplianceDBHelper
    private lateinit var rateDBHelper: RateDBHelper
    private lateinit var maxDBHelper: MaxDBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container:ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // inflate layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        // initialize db helpers
        applianceDBHelper = ApplianceDBHelper(requireActivity().applicationContext)
        rateDBHelper = RateDBHelper(requireActivity().applicationContext)
        maxDBHelper = MaxDBHelper(requireActivity().applicationContext)

        arrayList = applianceDBHelper.readAllAppliances()
        rateCost = rateDBHelper.readCost()
        max = maxDBHelper.readMax()
        binding!!.kwhMonthly.setText(max.toString())

        var counter:Int = 0
        var entertainment:Double = 0.0
        var lighting:Double = 0.0
        var cooling:Double = 0.0
        var kitchenApp:Double = 0.0
        var householdApp:Double = 0.0
        var other:Double = 0.0
        var total:Double = 0.0
        var dummyPrice:Double = 0.0
        var pieChart = binding!!.monthlyUsagePieChart
        // get calendar
        val calendar = Calendar.getInstance()
        // get total number of days of current month
        val daysOfThisMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        // get total number of weeks of current month
        val weeksOfThisMonth = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH)

        for (item in arrayList){

            when(arrayList[counter].frequency){
                // rating must be divided by 1000 since it needs to be converted from watts to kilowatts
                "Daily" -> dummyPrice = (arrayList[counter].rating.toDouble()/1000.0) * (arrayList[counter].duration.toDouble()) * daysOfThisMonth
                "Weekly" -> dummyPrice = (arrayList[counter].rating.toDouble()/1000.0) * (arrayList[counter].duration.toDouble()) * weeksOfThisMonth
                "Monthly" -> dummyPrice = (arrayList[counter].rating.toDouble()/1000.0) * (arrayList[counter].duration.toDouble())

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

        val entries = ArrayList<PieEntry>()
        val colorSet = java.util.ArrayList<Int>()

        if (total == 0.0){

            entries.add(PieEntry(100.0F))
            colorSet.add(Color.parseColor("#666666"))

        }
        else{

            if(entertainment>0){
                entries.add(PieEntry(entertainment.toFloat()))
                colorSet.add(ContextCompat.getColor(this.requireContext(), R.color.theme_red))
            }
            if(lighting>0){
                entries.add(PieEntry(lighting.toFloat()))
                colorSet.add(ContextCompat.getColor(this.requireContext(), R.color.theme_green))
            }
            if(cooling>0){
                entries.add(PieEntry(cooling.toFloat()))
                colorSet.add(ContextCompat.getColor(this.requireContext(), R.color.theme_blue))
            }
            if(kitchenApp>0){
                entries.add(PieEntry(kitchenApp.toFloat()))
                colorSet.add(ContextCompat.getColor(this.requireContext(), R.color.theme_pink))
            }
            if(householdApp>0){
                entries.add(PieEntry(householdApp.toFloat()))
                colorSet.add(ContextCompat.getColor(this.requireContext(), R.color.theme_yellow))
            }
            if(other>0){
                entries.add(PieEntry(other.toFloat()))
                colorSet.add(ContextCompat.getColor(this.requireContext(), R.color.theme_orange))
            }

        }

        val dataSet = PieDataSet(entries,"")
        dataSet.colors = colorSet

        val data = PieData(dataSet)

        if (total==0.0){

            dataSet.setDrawValues(false)

        }
        else{

            dataSet.setDrawValues(true)
            dataSet.valueTextColor = Color.WHITE
            dataSet.valueTextSize = 12.0F
            dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE;
            dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE;

        }

        pieChart.data = data
        pieChart.centerTextRadiusPercent = 0f
        pieChart.isDrawHoleEnabled = false
        pieChart.isUsePercentValuesEnabled
        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = false

        binding!!.dailyEle.text = String.format("%.2f", total/30) + " kW"
        binding!!.monthlyEle.text = String.format("%.2f", total) + " kW"
        binding!!.dailyCost.text = "PHP " + String.format("%.2f", ((total/daysOfThisMonth)*rateCost))
        binding!!.monthlyCost.text = "PHP " + String.format("%.2f", (total*rateCost))

        if(max.equals(0.0)){
            binding!!.usageLimitProgress.progress = 0
            binding!!.usageLimitText.text = "Not Set"
            binding!!.monthlyLimitText.text = "Not Set"
        }
        else{
            val progress = ((total/max)*100).toInt()
            binding!!.usageLimitProgress.progress = progress
            binding!!.usageLimitText.text = "$progress%"
            binding!!.monthlyLimitText.text = "${String.format("%.2f",total)} kW / ${String.format("%.2f",max)} kW"
        }
        if((total/max)*100 < 25){
            binding!!.monthlyLimitText.setTextColor(Color.GREEN)
        }
        else if((total/max)*100 < 75){
            binding!!.monthlyLimitText.setTextColor(Color.parseColor("#FFA500"))
        }
        else{
            binding!!.monthlyLimitText.setTextColor(Color.RED)
        }

        binding!!.setKwhMonthly.setOnClickListener(){

            if(binding!!.kwhMonthly.text.toString().trim().isNullOrBlank()){

                binding!!.kwhMonthly.error = "Field is Empty."

            }
            else if(binding!!.kwhMonthly.text.toString() == "0.0"){

                binding!!.kwhMonthly.error = "Cannot be zero."

            }
            else{

                val mSet = MediaPlayer.create(context,R.raw.set)
                maxDBHelper.deleteMax()
                val maxVal = binding!!.kwhMonthly.text.toString().toFloat()
                maxDBHelper.insertMax(maxVal)
                val test = maxDBHelper.readMax()
                mSet.start()
                val imm: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding!!.maxInputLayout.windowToken,0)
                Toast.makeText(context, "Maximum Threshold Set! $test",Toast.LENGTH_SHORT).show()

                val progress = ((total/maxVal)*100).toInt()
                max = maxDBHelper.readMax()
                if(progress < 25){
                    binding!!.monthlyLimitText.setTextColor(Color.GREEN)
                }
                else if(progress < 75){
                    binding!!.monthlyLimitText.setTextColor(Color.parseColor("#FFA500"))
                }
                else{
                    binding!!.monthlyLimitText.setTextColor(Color.RED)
                }
                binding!!.usageLimitProgress.progress = progress
                binding!!.usageLimitText.text = "$progress %"
                val formatTotal = String.format("%.2f",total)
                val formatMax = String.format("%.2f",max)
                binding!!.monthlyLimitText.text = "$formatTotal kW / $formatMax kW"

            }

        }

        return binding!!.root
    }

    // binding must be set to null on fragment destroy to prevent memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}