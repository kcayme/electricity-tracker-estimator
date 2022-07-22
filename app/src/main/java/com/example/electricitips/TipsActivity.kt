package com.example.electricitips

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.electricitips.databinding.ActivityTipsBinding

class TipsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //replaceFragment(homeFragment)

        // listener on the navigation bar
        /*binding.bottomNavView.setOnItemSelectedListener {
            // switch-case the id of the item selected in the navigation bar
            when (it.itemId) {
                R.id.home -> TODO()//replaceFragment(homeFragment)
                R.id.dashboard -> TODO()//replaceFragment(dashboardFragment)
                R.id.links -> TODO()//replaceFragment(linksFragment)
                R.id.tips -> TODO()//replaceFragment(tipsFragment)
            }
            true
        }*/
    }
}