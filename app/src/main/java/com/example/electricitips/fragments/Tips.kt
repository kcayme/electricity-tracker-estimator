package com.example.electricitips.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.electricitips.*
import com.example.electricitips.databinding.FragmentTipsBinding


class Tips :  Fragment(R.layout.fragment_tips){

    private lateinit var binding: FragmentTipsBinding
    private var webView: WebView? = null
    private var navController : NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container:ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate layout for this fragment
        binding = FragmentTipsBinding.inflate(inflater, container, false)

        webView = binding.webView
        webView!!.webViewClient = WebViewClient()

        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true

        //onLoadWebsites()
        binding.tipsBtn.setOnClickListener() {
            val action = TipsDirections.actionTipsToWebView2()
            try{
                //val transaction = parentFragmentManager.beginTransaction()
                //val newFrag = WebView()
                //transaction.replace(R.id.nav_host_fragment,newFrag)
                //transaction.commit()
                //val current = parentFragmentManager.getBackStackEntryAt(parentFragmentManager.backStackEntryCount-1)
                //var NavHostFrag = parentFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                //var navController = NavHostFrag.navController
                val action = TipsDirections.actionTipsToWebView2()
                navController = parentFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()
                navController!!.navigate(action)
            }
            catch (e: Exception){
                Toast.makeText(context,"${e.toString()}",Toast.LENGTH_LONG).show()
            }
            //navController controls the navigation between fragments
            //navController = navHostFragment.findNavController()
        }

        return binding.root
    }


    private fun onLoadWebsites() {

    /*
        binding.tipsBtn.setOnClickListener() {
            val action = TipsDirections.actionTipsToWebView2()
            findNavController().navigate(action)

            val tips: Array<String> = resources.getStringArray(R.array.tipsWebsites)
            AlertDialog.Builder(requireContext()) //requireContext is used when fragments are involved
                .setTitle("List of Tips")
                .setItems(tips) { _, which ->
                    if (tips[which] == "Tips by DOE") {
                        binding.webView.loadUrl("https://www.pna.gov.ph/articles/1095032")
                    }
                    if (tips[which] == "Tips by Energy Literacy PH") {
                        binding.webView.loadUrl("https://www.energyliteracyph.com/learning-materials/save-energy")
                    }
                    if (tips[which] == "Energy Efficiency Tips by MERALCO") {
                        binding.webView.loadUrl("https://company.meralco.com.ph/faq-about/energy-efficiency")
                    }
                    if (tips[which] == "No-cost Ways to Save Electricity") {
                        binding.webView.loadUrl("https://www.bchydro.com/powersmart/residential/tips-technologies/everyday-electricity-saving-tips.html")
                    }
                    if (tips[which] == "Understanding Energy Labels in Appliances") {
                        binding.webView.loadUrl("https://101appliance.com/understanding-the-energy-label/")
                    }
                }
                .show()
        }
        */
    }


}

