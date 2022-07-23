package com.example.electricitips.fragments


import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.electricitips.Appliance
import com.example.electricitips.R
import com.example.electricitips.RecyclerViewAdapter
import com.example.electricitips.databinding.FragmentDashboardBinding




class Dashboard: Fragment(R.layout.fragment_dashboard) {

    private var binding: FragmentDashboardBinding? = null
    private val romImgIds = arrayOf<Int>(R.drawable.ic_appliance,R.drawable.ic_appliance,R.drawable.ic_appliance,R.drawable.ic_appliance,R.drawable.ic_appliance)
    private val romanceTitles = arrayOf<String>("About Time", "Pride & Prejudice", "The Fault in Our Stars", "Crazy Rich Asians", "Me Before You")
    private val romanceRelease = arrayOf<String>("2013","2005","2014","2018","2016")
    private var arrayList = ArrayList<Appliance>()

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
            if(binding!!.chip1.isSelected){

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}