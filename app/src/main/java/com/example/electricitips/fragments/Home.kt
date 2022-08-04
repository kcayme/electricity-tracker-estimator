package com.example.electricitips.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
    private var rateCost:Float = 0.0F
    private var max: Float = 0.0F
    private lateinit var applianceDBHelper: ApplianceDBHelper
    private lateinit var rateDBHelper: RateDBHelper
    private lateinit var maxDBHelper: MaxDBHelper
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

        binding!!.dailyEle.text = String.format("%.2f", total/30) + "kW"
        binding!!.monthlyEle.text = String.format("%.2f", total) + "kW"
        binding!!.dailyCost.text = "PHP " + String.format("%.2f", ((total/30)*rateCost))
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
            binding!!.monthlyLimitText.text = "$total kW / $max kW"
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
                binding!!.usageLimitText.text = "$progress%"
                binding!!.monthlyLimitText.text = "$total kW / $max kW"

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