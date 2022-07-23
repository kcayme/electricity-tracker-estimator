package com.example.electricitips.fragments


import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.electricitips.R
import com.example.electricitips.databinding.FragmentDashboardBinding




class Dashboard: Fragment(R.layout.fragment_dashboard) {

    private var binding: FragmentDashboardBinding? = null

    private val romImgIds = arrayOf<Int>(R.drawable.ic_appliance,R.drawable.ic_appliance,R.drawable.ic_appliance,R.drawable.ic_appliance,R.drawable.ic_appliance)
    private val romanceTitles = arrayOf<String>("About Time", "Pride & Prejudice", "The Fault in Our Stars", "Crazy Rich Asians", "Me Before You")
    private val romanceRelease = arrayOf<String>("2013","2005","2014","2018","2016")

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

        //arrayList.add(MyData(R.drawable.ic_appliance, "About Time",  "2013"))
        //arrayList.add(MyData(R.drawable.ic_appliance, "Pride & Prejudice",  "2005"))
        //val romListAdapter = MyListAdapter(this, arrayList)
        //binding?.deviceList?.adapter = romListAdapter
        if(arguments != null){
            val bundle = arguments
            val data = bundle!!.getStringArrayList("data")
            //_binding!!.listHeader.text = data.toString()
        }
        /*

        val list = arrayListOf(
             "Antique ruby",
             "Bitter lemon",
             "Virat Kohli", "Rohit Sharma", "Steve Smith",
             "Kane Williamson", "Ross Taylor"
         )

         var adapter: ArrayAdapter<String> = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, list)

         binding.listView.adapter = adapter
         */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}