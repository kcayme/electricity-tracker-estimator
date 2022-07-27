package com.example.electricitips.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.example.electricitips.R
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import com.example.electricitips.databinding.FragmentTipsBinding

class Tips :  Fragment(R.layout.fragment_tips){

    private lateinit var binding: FragmentTipsBinding
    private var webView: WebView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container:ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate layout for this fragment
        binding = FragmentTipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated (view:View, savedInstanceState: Bundle? ) {
        super.onViewCreated(view, savedInstanceState)

        webView = binding.webView
        webView!!.webViewClient = WebViewClient()

        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true

        onLoadWebsites()
    }

    private fun onLoadWebsites() {
        binding.tipsBtn.setOnClickListener() {
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
    }
}

