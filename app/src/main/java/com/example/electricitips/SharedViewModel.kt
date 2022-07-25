package com.example.electricitips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// this is a ViewModel class that holds data (the added items and electricity rate input) that can be used by all fragments and main activity
class SharedViewModel : ViewModel() {
    private var arrayList : MutableLiveData<ArrayList<Appliance>> = MutableLiveData()
    private var electricityRate : MutableLiveData<Float> = MutableLiveData()

    var arrayListLive : LiveData<ArrayList<Appliance>> = arrayList
    var electricityRateLive = electricityRate


    fun setApplianceList (arrayListLive: ArrayList<Appliance>){
        arrayList.value = arrayListLive
    }

    fun setElectrticityRate (rateInput: Float){
        electricityRateLive.value = rateInput
    }
}